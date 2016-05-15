package com.github.kondaurovdev.jsonApi.core.api.request

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
import com.github.kondaurovdev.jsonApi.core.api.crudRequest
import com.github.kondaurovdev.jsonApi.core.table.filter.iFilter
import com.github.kondaurovdev.jsonApi.core.table.iTable
import com.github.kondaurovdev.jsonApi.core.table.methods.retrieve.Pager

object RetrieveRequest {
  def jsonReads(table: iTable) = {
    implicit val iFilterReads = iFilter.jsonReads(table)
    (
      (__ \ "pager").read[Pager] ~
      (__ \ "filters").readNullable[List[iFilter]] ~
      (__ \ "include").readNullable[List[String]]
    )(RetrieveRequest.apply _)
  }
}

case class RetrieveRequest(
                           pager: Pager,
                           filters: Option[List[iFilter]],
                           include: Option[List[String]]) extends crudRequest
