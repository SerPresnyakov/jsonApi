package com.github.kondaurovdev.jsonApi.core.utils

import play.api.libs.json.{Writes, Json, JsObject}

object JsonSchema {

  def getObj(fields: (String, JsObject)*)(required: String*): JsObject = {
    Json.obj(
      "type" -> "object",
      "properties" -> JsObject(fields.map(f => f._1 -> f._2)),
      "required" -> required
    )
  }

  def obj(): JsObject = {
    Json.obj(
      "type" -> "object"
    )
  }

  def getEnum[T](enum: Iterable[T])(implicit writes: Writes[T]): JsObject = {
    Json.obj("enum" -> enum)
  }

}
