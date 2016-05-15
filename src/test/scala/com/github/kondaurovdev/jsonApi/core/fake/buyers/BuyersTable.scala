package com.github.kondaurovdev.jsonApi.core.fake.buyers

import com.github.kondaurovdev.jsonApi.core.fake.products.ProductsTable
import com.github.kondaurovdev.jsonApi.core.field.types.StrField
import com.github.kondaurovdev.jsonApi.core.field.types.number.NumberField
import com.github.kondaurovdev.jsonApi.core.field.{Field, iField}
import com.github.kondaurovdev.jsonApi.core.table.iTable
import com.github.kondaurovdev.jsonApi.core.table.relation.{Relation, iRelation}
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
