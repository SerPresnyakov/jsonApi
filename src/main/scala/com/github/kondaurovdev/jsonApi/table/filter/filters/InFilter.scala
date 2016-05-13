package com.github.kondaurovdev.jsonApi.table.filter.filters

import org.postgresql.util.PGobject
import com.github.kondaurovdev.jsonApi.field.iField
import com.github.kondaurovdev.jsonApi.table.filter.{extractor, iFilter}

//object In extends extractor {
//  val name: String = "in"
//
//  def extract(field: iField, arg: String): Either[String, PGobject] = {
//    arg.split(',')
//  }
//}

case class InFilter(fieldPath: String, value: PGobject) extends iFilter {
  def dbOp: String = "in"
}
