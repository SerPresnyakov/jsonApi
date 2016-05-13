package com.github.kondaurovdev.jsonApi.api

import play.api.libs.json.{JsValue, JsString, JsArray, JsObject}
import com.github.kondaurovdev.jsonApi.api.request.{UpdateRequest, CreateRequest, RetrieveRequest}
import com.github.kondaurovdev.jsonApi.field.types.number.NumNorm
import com.github.kondaurovdev.jsonApi.table.filter.filters.EqualFilter
import com.github.kondaurovdev.jsonApi.table.iTableCrud

case class CrudApi(table: iTableCrud) {

  def doRequest(request: crudRequest): Either[JsValue, JsValue] = request match {

    case get: RetrieveRequest =>
      table.getAll(get.filters.getOrElse(List()): _*)

    case create: CreateRequest =>
      val forUpdate = false
      create.data match {
        case obj: JsObject =>
          for (
            parsed <- BodyParser(table, forUpdate = forUpdate).parseObj(obj).right;
            res <- table.insert(parsed: _*).right
          ) yield res
        case body@(_: JsArray) =>
          for (
            arr <- body.asOpt[Seq[JsObject]].toRight(JsString("JsArray may contain only JsObjects")).right;
            parsed <- BodyParser(table, forUpdate = forUpdate).parseArr(arr).right;
            res <- table.batchInsert(parsed).right
          ) yield res
        case _ => Left(errorObj("JsObject or JsArray[JsObject] expected"))
      }

    case update: UpdateRequest =>
      val forUpdate = true
      update.data match {
        case obj: JsObject =>
          for (
            id <- obj.value.get("id").toRight(JsString("id must be specified")).right;
            parsed <- BodyParser(table, forUpdate = forUpdate).parseObj(obj).right;
            res <- table.update(EqualFilter("id", NumNorm.getPgObject(id)))(parsed: _*).right
          ) yield res
        case body@(_: JsArray) =>
          for (
            arr <- body.asOpt[Seq[JsObject]].toRight(JsString("JsArray may contain only JsObjects")).right;
            parsed <- BodyParser(table, forUpdate = forUpdate).parseArr(arr).right;
            res <- table.batchUpdate(parsed).right
          ) yield res
        case _ => Left(errorObj("JsObject or JsArray[JsObject] expected"))
      }

    case _ => Left(errorObj("not impl"))

  }

}
