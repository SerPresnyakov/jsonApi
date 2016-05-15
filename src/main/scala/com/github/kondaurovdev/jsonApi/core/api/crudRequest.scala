package com.github.kondaurovdev.jsonApi.core.api

import play.api.libs.json._
import com.github.kondaurovdev.jsonApi.core.api.request.{CreateRequest, RetrieveRequest, UpdateRequest}
import com.github.kondaurovdev.jsonApi.core.table.iTable

object crudRequest {

  def jsonReads(table: iTable) = new Reads[crudRequest] {

    def reads(json: JsValue): JsResult[crudRequest] = {

      (json \ "method").asOpt[String] match {
        case Some("get") => json.validate(RetrieveRequest.jsonReads(table))
        case Some("create") => json.validate[CreateRequest]
        case Some("update") => json.validate[UpdateRequest]
        case None => JsError("method is required")
        case _ => JsError("supported methods: get, create, update")
      }

    }

  }

}

trait crudRequest
