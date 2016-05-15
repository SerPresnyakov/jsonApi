package com.github.kondaurovdev.jsonApi.core

import play.api.libs.json.{Json, JsObject, JsValue}

package object api {

  def errorObj(error: String, isInternal: Boolean = false, details: Option[JsValue] = None): JsObject = {
    Json.obj(
      "error" -> error,
      "isInternal" -> isInternal,
      "details" -> details
    )
  }


}
