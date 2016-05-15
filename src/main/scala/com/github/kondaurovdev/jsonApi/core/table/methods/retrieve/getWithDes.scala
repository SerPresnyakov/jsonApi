package com.github.kondaurovdev.jsonApi.core.table.methods.retrieve

import play.api.libs.json._
import com.github.kondaurovdev.jsonApi.core.field.types.number.NumNorm
import com.github.kondaurovdev.jsonApi.core.table.filter.filters.EqualFilter
import com.github.kondaurovdev.jsonApi.core.table.filter.iFilter

import scala.collection.mutable.ListBuffer

import com.github.kondaurovdev.jsonApi.core.api.errorObj

trait getWithDes[M] {

  this: retrieve =>

  def dbReads: Reads[M]
  def resourceName: String

  def findAll(where: iFilter*): Either[JsValue, List[M]] = {

    val resultList = ListBuffer.empty[M]
    val errors = ListBuffer.empty[JsValue]

    getAll(where: _*)(None).right.map(lst => {
      lst.value.foreach(_.validate(dbReads) match {
        case JsSuccess(res, _) => resultList += res
        case JsError(err) =>
          errors += JsError.toJson(err)
          None
      })
    })

    if (errors.isEmpty) {
      Right(resultList.result())
    } else {
      Left(errorObj(s"$resourceName: can't deserialize some objects", isInternal = true, Some(Json.toJson(errors))))
    }

  }

  def findOneOpt(where: iFilter*): Either[JsValue, Option[M]] = {

    getOne(where: _*).right.map {
      case None => Right(None)
      case Some(obj) => {
        obj.validate(dbReads).fold(
          errs => Left(errorObj(s"$resourceName: can't deserialize", isInternal = true, Some(JsError.toJson(errs)))),
          res => Right(Some(res))
        )
      }
    }.right.flatMap(r => r)

  }

  def findOne(where: iFilter*): Either[JsValue, M] = {
    for (
      opt <- findOneOpt(where: _*).right;
      res <- opt.toRight(JsString(s"$resourceName not found")).right
    ) yield res
  }

  def findByIdOpt(id: Long): Either[JsValue, Option[M]] = {
    findOneOpt(EqualFilter("id", NumNorm.getPgObject(id)))
  }

  def findById(id: Long): Either[JsValue, M] = {
    findOne(EqualFilter("base.id", NumNorm.getPgObject(id)))
  }

}
