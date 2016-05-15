package com.github.kondaurovdev.jsonApi.core.api.request

import play.api.libs.json.{Json, JsValue}
import com.github.kondaurovdev.jsonApi.core.api.crudRequest

object CreateRequest {
  implicit val jsonReads = Json.reads[CreateRequest]
}

case class CreateRequest(data: JsValue) extends crudRequest
