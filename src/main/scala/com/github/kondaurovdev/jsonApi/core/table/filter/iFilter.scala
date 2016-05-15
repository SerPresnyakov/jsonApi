package com.github.kondaurovdev.jsonApi.core.table.filter

import org.postgresql.util.PGobject
import play.api.libs.json._
import com.github.kondaurovdev.jsonApi.core.table.{TableHelper, iTable}
import com.github.kondaurovdev.jsonApi.core.table.filter.filters.EqualExtractor

object iFilter {

  def jsonReads(table: iTable) = new Reads[iFilter] {

    def reads(json: JsValue): JsResult[iFilter] = {
      (for (
        extr <- (json \ "op").asOpt[String].toRight("op is required").right.flatMap {
          case EqualExtractor.name => Right(EqualExtractor)
          case name => Left(s"Unknown operator $name")
        }.right;
        fieldName <- (json \ "field").asOpt[String].toRight("field is required").right.map(f => TableHelper.getFieldName(f)).right;
        field <- TableHelper.findField(fieldName)(table).right;
        value <- extr.extract(field.fieldType, fieldName, (json \ "value").asOpt[JsValue]).left.map(_.toString()).right
      ) yield value) match {
        case Left(err) => JsError(err)
        case Right(res) => JsSuccess(res)
      }
    }

  }

}

trait iFilter {
  def fieldPath: String
  def value: PGobject
  def dbOp: String
}


