package com.github.kondaurovdev.jsonApi.core.table.methods

package object insert {

  def getColumns(row: (Symbol, Any)*): (Seq[String], Seq[(Symbol, Any)])  = {
    val sorted = row.sortBy(_._1.name)
    val columns = sorted.map(_._1.name)
    columns -> sorted
  }

  def getSql(tableName: String, columns: Iterable[String]): String = {
    s"""
       |INSERT INTO $tableName (${columns.mkString(",")})
       |VALUES (${columns.map(v => s"{$v}").mkString(",")})
      """.stripMargin
  }

}
