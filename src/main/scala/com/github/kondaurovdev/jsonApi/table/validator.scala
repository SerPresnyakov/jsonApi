package com.github.kondaurovdev.jsonApi.table

import com.eclipsesource.schema.{SchemaType, SchemaValidator, _}
import play.api.libs.json._
import com.github.kondaurovdev.jsonApi.field.iField

import scala.collection.mutable.ListBuffer

object validator {

  def validate(_schema: JsValue, v: JsValue): Either[JsValue, JsValue] = {
    for (
      s <- getSchema(_schema).right;
      res <- validate(s, v).right
    ) yield res
  }

  def validate(_schema: SchemaType, v: JsValue): Either[JsValue, JsValue] = {
    SchemaValidator.validate(_schema)(v).asEither.left.map(JsError.toJson)
  }

  def getSchema(schema: JsValue): Either[JsValue, SchemaType] = {
    Json.fromJson[SchemaType](schema).asEither.left.map(JsError.toJson)
  }

  def buildSchema(fields: Iterable[iField], forUpdate: Boolean): JsObject = {

    val requiredFields = if (forUpdate) {
      Seq("id")
    } else {
      fields
        .filterNot(f => f.nullable || (f.hasDefault && f.fieldName == "id"))
        .map(_.name)
    }

    val _fields = ListBuffer.empty[(String, JsValue)]

    fields.foreach(f => {
      _fields += f.name -> f.fieldType.getSchema
    })

    Json.obj(
      "type" -> "object",
      "properties" -> JsObject(_fields),
      "required" -> requiredFields
    )

  }

}

trait validator {

  this: iTable =>

  import validator._

  lazy val forUpdateSchema = getSchema(buildSchema(fields, forUpdate = true))
  lazy val forInsertSchema = getSchema(buildSchema(fields, forUpdate = false))

  def validate(jsVal: JsValue, forUpdate: Boolean): Either[JsValue, JsValue] = {
    val schema = if (forUpdate) forUpdateSchema else forInsertSchema
    for (
      s <- schema.right;
      res <- validator.validate(s, jsVal).right
    ) yield res
  }

}
