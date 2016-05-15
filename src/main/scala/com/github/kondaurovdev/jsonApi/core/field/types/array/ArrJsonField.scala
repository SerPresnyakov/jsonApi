package com.github.kondaurovdev.jsonApi.core.field.types.array

import play.api.libs.json.{JsObject, JsValue, Json}
import com.github.kondaurovdev.jsonApi.core.field.types.JsonField
import com.github.kondaurovdev.jsonApi.core.table.validator

object ArrJsonField {
  val variant = "jsonArr"
  val dbType = "json[]"
  implicit val jsonFormat = Json.format[ArrJsonField]
}

case class ArrJsonField(schema: JsObject, isUnique: Boolean, minItems: Int, maxItems: Int) extends arrayField {

  val jsonSchema = validator.getSchema(schema)

  val arrayType: String = ArrJsonField.dbType
  val variant: String = ArrJsonField.variant

  def toObj: JsObject = Json.toJson(this).as[JsObject]

  def getSchema: JsObject = getSchema(this.schema)

  def parseOne(jsValue: JsValue) = {
    for (
      s <- jsonSchema.right;
      res <- JsonField.validate(s, jsValue).right
    ) yield res
  }

}
