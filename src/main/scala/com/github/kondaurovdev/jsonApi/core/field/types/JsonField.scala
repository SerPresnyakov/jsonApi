package com.github.kondaurovdev.jsonApi.core.field.types

import com.eclipsesource.schema.SchemaType
import org.postgresql.util.PGobject
import play.api.libs.json._
import com.github.kondaurovdev.jsonApi.core.field.fieldType
import com.github.kondaurovdev.jsonApi.core.table.validator
import com.github.kondaurovdev.jsonApi.core.utils.PgHelper

object JsonField {

  val variant = "json"
  val dbType = "json"

  implicit val jsonFormat = Json.format[JsonField]

  def validate(schema: SchemaType, value: JsValue): Either[JsValue, JsValue] = {
    validator.validate(schema, value)
  }

  def getPgObject[T](v: T)(implicit writes: Writes[T]): PGobject = {
    PgHelper.getPgObject(JsonField.dbType, Json.toJson(v))
  }

}

case class JsonField(schema: JsObject) extends fieldType {

  val _schema = validator.getSchema(schema)

  def isArray: Boolean = false

  val variant: String = JsonField.variant
  def toObj = Json.toJson(this).as[JsObject]

  def parse(jsValue: JsValue) = {
    for (
      s <- _schema.right;
      res <- JsonField.validate(s, jsValue).right
    ) yield JsonField.getPgObject(res)
  }

  def getSchema: JsObject = this.schema

}
