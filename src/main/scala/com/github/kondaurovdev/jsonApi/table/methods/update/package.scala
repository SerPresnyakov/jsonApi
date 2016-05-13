package com.github.kondaurovdev.jsonApi.table.methods

import com.github.kondaurovdev.jsonApi.table.methods.insert.{getColumns => insertGetColumns}

package object update {

  def getSql(tableName: String, columns: Iterable[String], where: String): String = {

    val setColumns = columns.filterNot(_ == "id")
      s"""
         |UPDATE $tableName
         |SET (${setColumns.mkString(",")}) = (${setColumns.map(c => s"{$c}").mkString(",")})
         |$where
          """.stripMargin
  }

  def getColumns(fields: Seq[(Symbol, Any)]): (Seq[String], Seq[(Symbol, Any)]) = insertGetColumns(fields: _*)

}
