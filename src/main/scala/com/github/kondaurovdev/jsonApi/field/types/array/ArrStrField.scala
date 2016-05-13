package com.github.kondaurovdev.jsonApi.field.types.array

import org.postgresql.util.PGobject
import play.api.libs.json.{Json, JsString, JsValue, JsObject}
import com.github.kondaurovdev.jsonApi.field.types.StrField
import com.github.kondaurovdev.jsonApi.utils.PgHelper

object ArrStrField {

  implicit val jsonFormat = Json.format[ArrStrField]

  val dbType = s"${StrField.dbType}[]"

  def getPgObject(arr: Iterable[String]): PGobject = {
    PgHelper.getPgObject(dbType, s"{${arr.mkString(",")}}")
  }

}

case class ArrStrField(isUnique: Boolean, minItems: Int, maxItems: Int) extends arrayField {

  val arrayType: String = ArrStrField.dbType
  val variant: String = ArrStrField.dbType

  def parseOne(jsValue: JsValue) = {
    jsValue
      .asOpt[JsString]
      .toRight(JsString("Not a string"))
  }

  def toObj: JsObject = Json.toJson(this).as[JsObject]

  def getSchema: JsObject = getSchema(StrField.getSchema)

}
