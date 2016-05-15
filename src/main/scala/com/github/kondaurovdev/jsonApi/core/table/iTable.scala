package com.github.kondaurovdev.jsonApi.core.table

import org.slf4j.LoggerFactory
import com.github.kondaurovdev.jsonApi.core.field.iField
import com.github.kondaurovdev.jsonApi.core.table.relation.iRelation

trait iTable extends validator {

  def tableName: String

  def fields: List[iField]
  def relations: List[iRelation]

  val pks = List("id")

  val log = LoggerFactory.getLogger(this.getClass)

}
