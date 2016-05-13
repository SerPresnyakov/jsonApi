package com.github.kondaurovdev.jsonApi

import play.api.libs.json.{Json, JsObject, JsValue}

package object api {

//  def withArrField(field: iField)(innerRoute: (arrayField) => Route): Route = {
//    field.fieldType match {
//      case arrField: arrayField => innerRoute(arrField)
//      case _ => badRequest(() => s"Field '${field.name}' isn't of array type")
//    }
//  }
//
//  def withFilters(innerRoute: (List[iFilter]) => Route)(implicit table: iTable): Route = {
//    parameter('filter.?) {
//      case filter@Some(f) =>
//        FilterParser()(table.fields, table.relations).fromQuery(f) match {
//          case Left(err) => badRequest(() => err)
//          case Right(res) => innerRoute(res)
//        }
//      case _ => innerRoute(List())
//    }
//  }

  def errorObj(error: String, isInternal: Boolean = false, details: Option[JsValue] = None): JsObject = {
    Json.obj(
      "error" -> error,
      "isInternal" -> isInternal,
      "details" -> details
    )
  }


}
