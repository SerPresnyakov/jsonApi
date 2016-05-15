package com.github.kondaurovdev.jsonApi.core.table.methods.retrieve

import play.api.libs.json.Json

object Pager {

  implicit val jsonReads = Json.reads[Pager]

  def one = Pager(1, 1)

}

case class Pager(page: Int, per: Int) {

  def getSql: String = s"OFFSET ${(page - 1) * per} LIMIT $per"

}
