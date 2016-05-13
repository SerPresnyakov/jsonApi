package com.github.kondaurovdev.jsonApi.api.request

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
import com.github.kondaurovdev.jsonApi.api.crudRequest
import com.github.kondaurovdev.jsonApi.table.filter.iFilter
import com.github.kondaurovdev.jsonApi.table.iTable

object RetrieveRequest {
  def jsonReads(table: iTable) = {
    implicit val iFilterReads = iFilter.jsonReads(table)
    (
      (__ \ "filters").readNullable[List[iFilter]] ~
      (__ \ "include").readNullable[List[String]]
    )(RetrieveRequest.apply _)
  }
}

case class RetrieveRequest(
                           filters: Option[List[iFilter]],
                           include: Option[List[String]]) extends crudRequest
