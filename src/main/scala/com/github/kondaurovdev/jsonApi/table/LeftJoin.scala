package com.github.kondaurovdev.jsonApi.table

case class LeftJoin(table: String, alias: String, on: List[(String, String)], hasMany: Boolean = false) {
  def getSql: String = {
    s"LEFT JOIN $table AS $alias ON ${on.map(t => s"${t._1} = ${t._2}").mkString(" AND ")}"
  }
}
