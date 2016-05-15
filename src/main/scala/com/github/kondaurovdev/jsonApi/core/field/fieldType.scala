package com.github.kondaurovdev.jsonApi.core.field

import org.postgresql.util.PGobject
import play.api.libs.json._
import com.github.kondaurovdev.jsonApi.core.field.types.array.ArrJsonField
import com.github.kondaurovdev.jsonApi.core.field.types.number.NumberField
import com.github.kondaurovdev.jsonApi.core.field.types.{BoolField, JsonField, StrField}

object fieldType {

  val variant = "variant"

  implicit val jsonFormat = new Format[fieldType] {
    def reads(json: JsValue): JsResult[fieldType] = {
      (json \ "variant").asOpt[String] match {
        case Some(BoolField.variant) => JsSuccess(BoolField())
        case Some(JsonField.variant) => json.validate[JsonField]
        case Some(NumberField.variant) => json.validate[NumberField]
        case Some(StrField.variant) => JsSuccess(StrField())
        case _ => JsError("Unknown field type")
      }
    }

    def writes(o: fieldType): JsValue = Json.obj(
      variant -> o.variant
    ) ++ o.toObj
  }

  def allVariants: List[String] = List(
    BoolField.variant,
    JsonField.variant,
    NumberField.variant,
    StrField.variant,
    ArrJsonField.variant
  )

}

trait fieldType {

  def variant: String
  def toObj: JsObject
  def getSchema: JsObject
  def isArray: Boolean

  def parse(jsValue: JsValue): Either[JsValue, PGobject]

}




