package com.github.kondaurovdev.jsonApi.field.types

import play.api.libs.json.{JsString, JsObject, JsValue, Json}
import com.github.kondaurovdev.jsonApi.field.fieldType
import com.github.kondaurovdev.jsonApi.utils.PgHelper

object StrField {

  val variant = "str"
  val dbType = "varchar"

  def getSchema = Json.obj(
    "type" -> "string"
  )

  def getPgObject(s: String) = {
    PgHelper.getPgObject(StrField.dbType, s)
  }

}

case class StrField() extends fieldType {
  def isArray: Boolean = false
  val variant: String = StrField.variant
  def toObj = Json.obj()

  def parse(jsValue: JsValue) = {
    jsValue.asOpt[String]
      .toRight(JsString("Not a string"))
      .right
      .map(StrField.getPgObject)
  }

  def getSchema: JsObject = StrField.getSchema
}
