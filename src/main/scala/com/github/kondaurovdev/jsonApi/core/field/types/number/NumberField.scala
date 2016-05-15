package com.github.kondaurovdev.jsonApi.core.field.types.number

import play.api.libs.json._
import com.github.kondaurovdev.jsonApi.core.field.fieldType

object NumberField {
  val variant = "number"
  implicit val jsonFormat = Json.format[NumberField]

  def pk = NumberField(NumNorm)

  def getSchema = Json.obj(
    "type" -> "number"
  )

  def isNumber(s: String): Boolean = {
    s.forall(_.isDigit)
  }

}

case class NumberField(size: numSize) extends fieldType {

  def isArray: Boolean = false

  val variant: String = NumberField.variant
  def toObj = Json.toJson(this).as[JsObject]

  def parse(jsValue: JsValue) = {
    jsValue.asOpt[JsNumber].map(n => {
      size.getPgObject(n.value)
    }).toRight(JsString("Not a number"))
  }

  def getSchema: JsObject = NumberField.getSchema

}

