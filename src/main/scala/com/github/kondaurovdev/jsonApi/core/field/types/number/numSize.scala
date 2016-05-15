package com.github.kondaurovdev.jsonApi.core.field.types.number

import play.api.libs.json._
import com.github.kondaurovdev.jsonApi.core.utils.PgHelper

object numSize {
  implicit val format = new Format[numSize] {
    def writes(o: numSize): JsValue = JsString(o.variant)
    def reads(json: JsValue): JsResult[numSize] = json.asOpt[String].map {
      case NumSmall.`variant` => JsSuccess(NumSmall)
      case NumNorm.`variant` => JsSuccess(NumNorm)
      case NumBig.`variant` => JsSuccess(NumBig)
      case _ => JsError(s"Possible variants: ${NumSmall.variant}, ${NumNorm.variant}, ${NumBig.variant}")
    }.getOrElse(JsError("Number expected"))
  }
}

sealed trait numSize {
  val variant: String
  val dbType: String
  def getPgObject(num: Any) = PgHelper.getPgObject(dbType, num.toString)
}

case object NumSmall extends numSize {
  val variant: String = "small"
  val dbType = "int2"
}

case object NumNorm extends numSize {
  val variant: String = "mid"
  val dbType = "int4"
}

case object NumBig extends numSize {
  val variant: String = "big"
  val dbType = "int8"
}