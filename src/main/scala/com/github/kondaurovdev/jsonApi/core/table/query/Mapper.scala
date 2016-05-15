package com.github.kondaurovdev.jsonApi.core.table.query

import play.api.libs.json.Json.JsValueWrapper
import play.api.libs.json._
import com.github.kondaurovdev.jsonApi.core.table.iTable
import scala.collection.mutable.ListBuffer

import com.github.kondaurovdev.jsonApi.core.table.fromAlias

object Mapper {

  def mapRow(row: JsObject, tablePrefix: String, table: iTable, builtRelations: List[String] = List.empty[String]): Either[JsValue, JsObject] = {

    val obj = ListBuffer.empty[(String, JsValue)]
    val errors = ListBuffer.empty[(String, JsValueWrapper)]

    val rowObj = row.value.find(_._1.equalsIgnoreCase(tablePrefix)).flatMap(_._2.asOpt[JsObject])

    table.fields.foreach(f => {
      rowObj.foreach(baseObj => {
        baseObj.value.get(f.fieldName) match {
          case Some(v) => obj += f.name -> v
          case _ => if (!f.nullable) errors += f.fieldName -> "field is missing"
        }
      })
    })

    table.relations.foreach {

      case r if !builtRelations.contains(r.rightTable.tableName) =>

        if (r.hasMany) {

          row.value.find(_._1.equalsIgnoreCase(s"${tablePrefix}_${r.name}")).map(_._2) match {
            case Some(JsArray(arr)) =>
              val sub = ListBuffer.empty[JsValue]
              val subErrors = ListBuffer.empty[(String, JsValueWrapper)]

              arr.zipWithIndex.foreach {
                case (o: JsObject, key) => mapRow(Json.obj(fromAlias -> o), fromAlias, r.rightTable, builtRelations :+ table.tableName) match {
                  case Left(fail) => subErrors += key.toString -> fail
                  case Right(succ) => sub += succ
                }
                case _ =>
              }

              if (subErrors.isEmpty) {
                obj += r.name -> JsArray(sub)
              } else {
                errors += r.name -> Json.obj(subErrors: _*)
              }

            case _ => obj += r.name -> JsArray()
          }

        } else {
          mapRow(row, s"${tablePrefix}_${r.name}", r.rightTable, builtRelations :+ r.rightTable.tableName).fold(
            err => errors += s"${tablePrefix}_${r.name}" -> err,
            succ => obj += r.name -> succ
          )
        }

      case _ =>
    }

    if (errors.isEmpty) Right(JsObject(obj)) else Left(Json.obj(errors: _*))

  }

}
