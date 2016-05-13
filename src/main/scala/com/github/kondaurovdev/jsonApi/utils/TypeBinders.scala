package com.github.kondaurovdev.jsonApi.utils

import java.sql.ResultSet

import play.api.libs.json.{JsObject, JsValue, Json}
import scalikejdbc.TypeBinder

object TypeBinders {

  def getJsValue(obj: Object): Option[JsValue] = {
    util.Try {
      Option(obj.asInstanceOf[org.postgresql.util.PGobject].getValue).map(s => {
        Json.parse(s)
      })
    } match {
      case util.Success(res) => res
      case util.Failure(err) => None
    }
  }

  object JsValue extends TypeBinder[JsValue] {
    def apply(rs: ResultSet, columnIndex: Int): JsValue = JsValueOpt.apply(rs, columnIndex).get
    def apply(rs: ResultSet, columnLabel: String): JsValue = JsValueOpt.apply(rs, columnLabel).get
  }

  object JsValueOpt extends TypeBinder[Option[JsValue]] {
    def apply(rs: ResultSet, columnIndex: Int): Option[JsValue] = getJsValue(rs.getObject(columnIndex))
    def apply(rs: ResultSet, columnLabel: String): Option[JsValue] = getJsValue(rs.getObject(columnLabel))
  }

  object JsonObj extends TypeBinder[JsObject] {
    def apply(rs: ResultSet, columnIndex: Int): JsObject = Helper.normalizeObjFields(RowJsonObj.apply(rs, columnIndex))
    def apply(rs: ResultSet, columnLabel: String): JsObject = Helper.normalizeObjFields(RowJsonObj.apply(rs, columnLabel))
  }

  object RowJsonObj extends TypeBinder[JsObject] {
    def apply(rs: ResultSet, columnIndex: Int): JsObject = JsValueOpt.apply(rs, columnIndex).get.as[JsObject]
    def apply(rs: ResultSet, columnLabel: String): JsObject = JsValueOpt.apply(rs, columnLabel).get.as[JsObject]
  }

}
