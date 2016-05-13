package com.github.kondaurovdev.jsonApi.fake.buyers

import com.github.kondaurovdev.jsonApi.fake.products.ProductsTable
import com.github.kondaurovdev.jsonApi.field.types.StrField
import com.github.kondaurovdev.jsonApi.field.types.number.NumberField
import com.github.kondaurovdev.jsonApi.field.{Field, iField}
import com.github.kondaurovdev.jsonApi.table.iTable
import com.github.kondaurovdev.jsonApi.table.relation.{Relation, iRelation}
import play.api.libs.json.Json

object BuyersTable extends iTable {

  object Relations {
    val product = Relation("product", Json.obj(
      "left.productId" -> "right.id"
    ), ProductsTable)
  }

  def tableName: String = "buyers"

  def relations: List[iRelation] = List(
    Relations.product
  )

  def fields: List[iField] = List(
    Field("id", "id", NumberField.pk, hasDefault = true),
    Field("name", "name", StrField(), hasDefault = true),
    Field("productId", "product_id", NumberField.pk)
  )
}
