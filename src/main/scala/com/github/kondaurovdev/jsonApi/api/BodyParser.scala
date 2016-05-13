package com.github.kondaurovdev.jsonApi.api

import org.postgresql.util.PGobject
import play.api.libs.json.Json.JsValueWrapper
import play.api.libs.json._

import scala.collection.mutable.ListBuffer
import com.github.kondaurovdev.jsonApi.table.{bindings, iTable}

case class BodyParser(table: iTable, forUpdate: Boolean) {

  def parseArr(arr: Seq[JsObject]): Either[JsValue, Seq[bindings]] = {

    val errors = ListBuffer.empty[JsValueWrapper]
    val result = ListBuffer.empty[bindings]

    arr.zipWithIndex.foreach { case (obj, key) =>
      parseObj(obj).fold(
        err => errors += err,
        res => result += res
      )
    }

    if (errors.isEmpty) {
      Right(result.result())
    } else {
      Left(Json.arr(errors: _*))
    }

  }

  def parseObj(obj: JsObject): Either[JsValue, bindings] = {

    table.validate(obj, forUpdate).right.flatMap(_ => {

      val result = ListBuffer.empty[(Symbol, PGobject)]
      val errors = ListBuffer.empty[JsValueWrapper]

      table.fields
        .foreach(f => {
          obj.fields.find(_._1 == f.name) match {
            case Some(v) =>
              f.fieldType.parse(v._2).fold(
                err => errors += err,
                res => Symbol(f.fieldName) -> res
              )
            case _ => if (!forUpdate && !f.hasDefault) errors += s"Field '${f.name}' missing"
          }
        })
      if (errors.isEmpty) {
        Right(result.result())
      } else {
        Left(Json.arr(errors: _*))
      }

    })

  }

}
