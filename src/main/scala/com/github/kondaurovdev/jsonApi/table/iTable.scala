package com.github.kondaurovdev.jsonApi.table

import org.slf4j.LoggerFactory
import com.github.kondaurovdev.jsonApi.field.iField
import com.github.kondaurovdev.jsonApi.table.relation.iRelation

trait iTable extends validator {

  def tableName: String

  def fields: List[iField]
  def relations: List[iRelation]

  val pks = List("id")

  val log = LoggerFactory.getLogger(this.getClass)

}
