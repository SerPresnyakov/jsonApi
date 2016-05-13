package com.github.kondaurovdev.jsonApi.api.request

import play.api.libs.json.{Json, JsValue}
import com.github.kondaurovdev.jsonApi.api.crudRequest

object UpdateRequest {
  implicit val jsonReads = Json.reads[UpdateRequest]
}

case class UpdateRequest(data: JsValue) extends crudRequest