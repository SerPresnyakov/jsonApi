package com.github.kondaurovdev.jsonApi.api.request

import play.api.libs.json.{Json, JsValue}
import com.github.kondaurovdev.jsonApi.api.crudRequest

object CreateRequest {
  implicit val jsonReads = Json.reads[CreateRequest]
}

case class CreateRequest(data: JsValue) extends crudRequest
