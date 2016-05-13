package com.github.kondaurovdev.jsonApi.table.filter.filters

import org.postgresql.util.PGobject
import play.api.libs.json.{JsString, JsValue}
import com.github.kondaurovdev.jsonApi.field.fieldType
import com.github.kondaurovdev.jsonApi.table.filter.{extractor, iFilter}

object EqualExtractor extends extractor {

  val name = "eq"

  def extract(fieldType: fieldType, fieldPath: String, arg: Option[JsValue]): Either[JsValue, iFilter] = {
    for (
      value <- arg.toRight(JsString("value required")).right;
      parsed <- fieldType.parse(value).right
    ) yield EqualFilter.apply(fieldPath, parsed)
  }

}

case class EqualFilter(fieldPath: String, value: PGobject) extends iFilter {
  def dbOp: String = "="
}
