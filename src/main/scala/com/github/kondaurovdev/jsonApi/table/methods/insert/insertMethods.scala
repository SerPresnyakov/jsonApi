package com.github.kondaurovdev.jsonApi.table.methods.insert

import org.postgresql.util.{PSQLException, PGobject}
import play.api.libs.json.{JsNumber, JsString, Json, JsValue}
import com.github.kondaurovdev.jsonApi.table._
import com.github.kondaurovdev.jsonApi.table.methods.RowHelper
import scalikejdbc.SQL

import scala.util.control.Breaks._

import com.github.kondaurovdev.jsonApi.api.errorObj

trait insertMethods {

  this: iTableCrud =>

  def insert(row: (Symbol, PGobject)*): Either[JsValue, JsNumber] = {

    val (columns, bindings) = getColumns(row: _*)

    if (columns.isEmpty) {
      Left(errorObj("insert error: no fields specified"))
    } else {
      db.withAutoCommit { implicit s =>
        util.Try {
          SQL(getSql(tableName, columns))
            .bindByName(bindings: _*)
            .updateAndReturnGeneratedKey("id").apply()
        } match {
          case util.Success(id) => Right(JsNumber(id))
          case util.Failure(err: PSQLException) => Left(errorObj(s"insert error, ${err.getSQLState}", details = Some(JsString(err.getMessage))))
          case err => Left(errorObj(s"insert error: $err", isInternal = true))
        }
      }
    }

  }

  def batchInsert(rows: Iterable[Seq[(Symbol, PGobject)]]): Either[JsValue, JsValue] = {

    println("rows\n" + rows)

    val grouped = RowHelper.groupForBatch(rows)

    if (grouped.isEmpty) {
      Left(errorObj("batch insert: no rows specified"))
    } else {
      var error = Option.empty[JsValue]
      breakable {
        grouped.foreach(groupRows => {
          val columns = groupRows.head.map(_._1.name)
          db.autoCommit { implicit s =>
            util.Try {
              val sql = getSql(tableName, columns)
              println("sql\n" + sql)
              SQL(sql).batchByName(groupRows: _*).apply()
            } match {
              case util.Failure(err: PSQLException) =>
                error = Some(errorObj(s"batchInsert error, ${err.getSQLState}", isInternal = false, details = Some(JsString(err.getMessage))))
                break()
              case util.Success(res) => res
              case err => error = Some(errorObj(s"batchInsert error: $err", isInternal = true))
            }
          }
        })
      }

      error.toLeft(Json.obj(
        "inserted" -> rows.size
      ))
    }

  }

}
