package com.github.kondaurovdev.jsonApi.field.types.array

import org.postgresql.util.PGobject
import play.api.libs.json._
import com.github.kondaurovdev.jsonApi.field.fieldType
import com.github.kondaurovdev.jsonApi.utils.PgHelper

import scala.collection.mutable.ListBuffer

trait arrayField extends fieldType {

  val isUnique: Boolean
  val maxItems: Int
  val minItems: Int

  val arrayType: String

  def getSchema(schema: JsObject): JsObject = {
    Json.obj(
      "type" -> "array",
      "isUnique" -> isUnique,
      "min" -> minItems,
      "max" -> maxItems,
      "items" -> schema
    )
  }

  val isArray = true

  def parseOne(jsValue: JsValue): Either[JsValue, JsValue]

  def parse(jsValue: JsValue): Either[JsValue, PGobject] = {

    jsValue.asOpt[JsArray]
      .toRight(JsString("Not an array"))
      .right.flatMap(arr => {
        if (arr.value.length > maxItems) {
          Left(JsString(s"Array may contain no more than '$maxItems' items"))
        } else if (arr.value.length < minItems) {
          Left(JsString(s"Array must contain at least '$minItems' items"))
        } else {

          val result = ListBuffer.empty[Any]
          val errors = ListBuffer.empty[JsValue]

          arr.value.zipWithIndex.foreach { case (value, i) =>
            parseOne(value).fold(
              err => errors += err,
              res => result += res
            )
          }

          if (errors.nonEmpty) {
            Left(Json.arr(errors))
          } else {
            Right(PgHelper.getPgObject(this.arrayType, s"{${result.mkString(",")}}"))
          }

        }
    })

  }

}
