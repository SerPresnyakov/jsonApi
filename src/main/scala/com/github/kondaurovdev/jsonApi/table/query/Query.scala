package com.github.kondaurovdev.jsonApi.table.query

import org.postgresql.util.PGobject
import play.api.libs.json.Json.JsValueWrapper
import play.api.libs.json.{Json, JsValue}
import com.github.kondaurovdev.jsonApi.table._
import com.github.kondaurovdev.jsonApi.table.filter.iFilter

import scala.collection.mutable.ListBuffer

import scala.util.control.Breaks._

import com.github.kondaurovdev.jsonApi.table.relation.iRelation

object Query {

  def getAggSubquery(leftTable: iTable, r: iRelation, builtRelations: List[String]): Either[JsValue, String] = {

    for (
      sql <- buildSql(r.rightTable, builtRelations).right;
      onFields <- r.getOnFields("right", leftTable).right
    ) yield {

      val columns = onFields.map(_.fieldName) ::: List(s"array_agg($fromAlias) as $aggAlias")

      s"""
         |(
         |SELECT
         |${columns.mkString(",\n")}
         |FROM (
         |$sql
         |) as agg
         |GROUP BY ${onFields.map(_.fieldName).mkString(",")}
         |)
        """.stripMargin

    }

  }

  def getJoins(leftTable: iTable, leftTableAlias: String, builtRelations: List[String] = List()): Either[JsValue, List[LeftJoin]] = {

    val result = ListBuffer.empty[LeftJoin]
    val errors = ListBuffer.empty[JsValueWrapper]

    breakable {
      leftTable.relations.foreach {
        case r if !builtRelations.contains(r.rightTable.tableName) =>
          r.getOnClause(leftTable, leftTableAlias) match {
            case Left(err) =>
              errors += Json.obj(
                "error" -> s"can't get 'on' clause",
                "details" -> err,
                "path" -> leftTableAlias
              )
              break()
            case Right((on, bindings)) =>
              val alias = r.getAlias(leftTableAlias)
              if (!r.hasMany) {
                result += LeftJoin(r.rightTable.tableName, alias, on)
                getJoins(r.rightTable, alias, builtRelations :+ leftTable.tableName) match {
                  case Left(err) =>
                    errors += Json.obj(
                      "error" -> s"can't get joins for table ${leftTable.tableName}",
                      "details" -> err,
                      "path" -> leftTableAlias
                    )
                    break()
                  case Right(res) => result ++= res
                }
              } else {
                getAggSubquery(leftTable, r, builtRelations :+ leftTable.tableName) match {
                  case Left(err) => errors += err
                  case Right(res) => result += LeftJoin(res, alias, on, hasMany = true)
                }
              }
          }
        case _ =>
      }
    }

    if (errors.isEmpty) {
      Right(result.result())
    } else {
      Left(Json.arr(errors: _*))
    }

  }

  def buildSql(fromTable: iTable, builtRelations: List[String] = List()): Either[JsValue, String] = {

    getJoins(fromTable, fromAlias, builtRelations) match {

      case Left(err) => Left(Json.obj("error" -> "can't get joins", "details" -> err))
      case Right(joins) =>

        val columns = fromTable.fields.map(_.fieldName).map(c => s"$fromAlias.$c") ::: List(fromAlias) ::: joins.map(j => {
          if (!j.hasMany) j.alias else s"${j.alias}.$aggAlias as ${j.alias}"
        })

        val sql =
          s"""
             |SELECT
             |${columns.mkString(",\n")}
             |FROM ${fromTable.tableName} as $fromAlias
             |${joins.map(_.getSql).mkString("\n")}
          """.stripMargin

        Right(sql)

    }

  }

  def getFilters(filters: iFilter*): (String, Seq[(Symbol, PGobject)]) = {

    if (filters.isEmpty) {
      ("", Seq())
    } else {
      var columns = ListBuffer.empty[String]
      val bindings = ListBuffer.empty[(Symbol, PGobject)]

      filters.foreach(f => {
        val bindName = f.fieldPath.replace(".", "_")
        columns += s"${f.fieldPath} ${f.dbOp} {$bindName}"
        bindings += Symbol(bindName) -> f.value
      })

      (s"WHERE ${columns.mkString(" AND ")}", bindings)
    }

  }

}
