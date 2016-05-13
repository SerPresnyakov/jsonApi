package com.github.kondaurovdev.jsonApi.table.filter

import play.api.libs.json.JsValue
import com.github.kondaurovdev.jsonApi.field.fieldType

trait extractor {
  val name: String
  def extract(fieldType: fieldType, fieldPath: String, arg: Option[JsValue]): Either[JsValue, iFilter]
}
