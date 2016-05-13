package com.github.kondaurovdev.jsonApi.table.methods.delete

import play.api.libs.json.JsValue
import com.github.kondaurovdev.jsonApi.table.iTableCrud
import scalikejdbc.SQL

import com.github.kondaurovdev.jsonApi.api.errorObj

trait delete {

  this: iTableCrud =>

  def deleteRow(id: Long): Either[JsValue, Boolean] = db.withAutoCommit { implicit s =>
    util.Try {
      SQL(s"DELETE $tableName WHERE id = $id").executeUpdate().apply()
    } match {
      case util.Success(res) => Right(res == 1)
      case util.Failure(err) => Left(errorObj(s"delete error: $err", isInternal = true))
    }
  }

}
