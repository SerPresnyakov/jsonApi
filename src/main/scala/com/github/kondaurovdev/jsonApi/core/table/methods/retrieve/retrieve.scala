package com.github.kondaurovdev.jsonApi.core.table.methods.retrieve

import play.api.libs.json.{JsArray, JsValue, Json}
import com.github.kondaurovdev.jsonApi.core.field.types.number.NumNorm
import com.github.kondaurovdev.jsonApi.core.table.filter.filters.EqualFilter
import com.github.kondaurovdev.jsonApi.core.table.filter.iFilter
import com.github.kondaurovdev.jsonApi.core.table.iTableCrud
import com.github.kondaurovdev.jsonApi.core.utils.TypeBinders
import TypeBinders._
import com.github.kondaurovdev.jsonApi.core.table.query.{Mapper, Query}
import scalikejdbc.SQL

import com.github.kondaurovdev.jsonApi.core.api.errorObj

import com.github.kondaurovdev.jsonApi.core.table.fromAlias

trait retrieve {

  this: iTableCrud =>

  def getAll(filters: iFilter*)(pager: Option[Pager]): Either[JsValue, JsArray] = db.withReadOnly { implicit s =>

    Query.buildSql(this) match {
      case Left(err) => Left(errorObj("can't build query", isInternal = true, details = Some(err)))
      case Right(query) =>
        val (where, whereBindings) = Query.getFilters(filters: _*)
        val limit = pager.map(_.getSql).getOrElse("")

        val sql =
          s"""
             |SELECT row_to_json(sub) as r
             |FROM
             |(
             | $query
             | $where
             | $limit
             |) as sub
        """.stripMargin

        log.debug(s"sql: $sql")

        util.Try {
          SQL(sql).bindByName(whereBindings: _*).map(wr => {
            Mapper.mapRow(wr.get("r")(RowJsonObj), fromAlias, this)
          }).list().apply()
        } match {
          case util.Success(res) =>
            val errors = res.filter(_.isLeft).map(_.left.get)
            if (errors.isEmpty) {
              Right(JsArray(res.map(_.right.get)))
            } else {
              Left(errorObj(s"Deserialize errors", isInternal = true, Some(Json.arr(errors))))
            }
          case util.Failure(err) => Left(errorObj(s"$err", isInternal = true))
        }

    }

  }

  def getOne(filters: iFilter*): Either[JsValue, Option[JsValue]] = getAll(filters: _*)(None).right.map(_.value.headOption)

  def getById(id: Long): Either[JsValue, Option[JsValue]] = getOne(EqualFilter("id", NumNorm.getPgObject(id)))

}
