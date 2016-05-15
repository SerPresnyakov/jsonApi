package com.github.kondaurovdev.jsonApi.core.api.request

import play.api.libs.json.{Json, JsValue}
import com.github.kondaurovdev.jsonApi.core.api.crudRequest

object UpdateRequest {
  implicit val jsonReads = Json.reads[UpdateRequest]
}

case class UpdateRequest(data: JsValue) extends crudRequest