package com.github.kondaurovdev.jsonApi.field.types

import play.api.libs.json._
import com.github.kondaurovdev.jsonApi.field.fieldType
import com.github.kondaurovdev.jsonApi.utils.PgHelper

import scala.util.Try

object BoolField {
  val variant = "bool"
  val dbType = "bool"
  def getSchema = Json.obj(
    "type" -> "boolean"
  )
  def fromStr(s: String): Either[String, Boolean] = {
    Try(s.toBoolean).toOption.toRight("Not a boolean")
  }
  def getPgObject(s: Boolean) = PgHelper.getPgObject(BoolField.dbType, s)
}

case class BoolField() extends fieldType {
  def isArray = false
  val variant: String = BoolField.variant
  def toObj: JsObject = Json.obj()
  def parse(jsValue: JsValue) = {
    jsValue
      .asOpt[Boolean]
      .toRight(JsString("Not a boolean"))
      .right.map(b => PgHelper.getPgObject(BoolField.dbType, b.toString))
  }

  def getSchema: JsObject = BoolField.getSchema
}