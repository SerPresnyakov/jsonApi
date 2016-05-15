package com.github.kondaurovdev.jsonApi.core.table.methods.update

import org.postgresql.util.PGobject
import play.api.libs.json.{JsBoolean, Json, JsValue}
import com.github.kondaurovdev.jsonApi.core.field.types.number.NumNorm
import com.github.kondaurovdev.jsonApi.core.table.filter.iFilter
import com.github.kondaurovdev.jsonApi.core.table.iTableCrud
import com.github.kondaurovdev.jsonApi.core.table.query.Query
import scalikejdbc.SQL
import com.github.kondaurovdev.jsonApi.core.table.filter.filters.EqualFilter
import com.github.kondaurovdev.jsonApi.core.table.methods.RowHelper

import com.github.kondaurovdev.jsonApi.core.api.errorObj

import scala.util.control.Breaks._

trait update {

  this: iTableCrud =>

  def update(_where: iFilter*)(fields: (Symbol, PGobject)*): Either[JsValue, JsBoolean] = {

    val (where, whereBindings) = Query.getFilters(_where: _*)
    val (columns, columnBindings) = getColumns(fields)
    val bindings = columnBindings ++: whereBindings

    if (columns.isEmpty) {
      Left(errorObj("update: no fields specified"))
    } else if (_where.isEmpty) {
      Left(errorObj("update: where clause must be specified at least on one field "))
    } else {
      db.withAutoCommit { implicit s =>
        util.Try {
          SQL(getSql(tableName, columns, where)).bindByName(bindings: _*).executeUpdate().apply()
        } match {
          case util.Success(res) => Right(JsBoolean(res == 1))
          case util.Failure(err) => Left(errorObj(s"update error: $err", isInternal = true))
        }
      }
    }

  }

  def batchUpdate(rows: Iterable[Seq[(Symbol, PGobject)]]): Either[JsValue, JsValue] = {

    val grouped = RowHelper.groupForBatch(rows)

    if (grouped.isEmpty) {
      Left(errorObj("batch update: no rows specified"))
    } else {

      var error = Option.empty[JsValue]

      breakable {
        grouped.foreach(groupRows => {
          val columns = groupRows.head.map(_._1.name)
          db.autoCommit { implicit s =>
            util.Try {
              SQL(getSql(tableName, columns, "WHERE id = {id}"))
                .batchByName(groupRows: _*)
                .apply()
            } match {
              case util.Failure(err) =>
                error = Some(errorObj(s"batch update: $err", isInternal = true))
                break()
              case _ =>
            }
          } match {
            case Left(err) => error = Some(err); break();
            case _ =>
          }
        })
      }

      error.toLeft(Json.obj(
        "updated" -> rows.size
      ))

    }

  }

  def updateById(id: Long)(fields: (Symbol, PGobject)*): Either[JsValue, JsBoolean] = {
    update(EqualFilter("id", NumNorm.getPgObject(id)))(fields: _*)
  }

}
