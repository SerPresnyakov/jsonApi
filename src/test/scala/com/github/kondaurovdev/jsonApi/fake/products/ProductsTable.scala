package com.github.kondaurovdev.jsonApi.fake.products

import com.github.kondaurovdev.jsonApi.fake.buyers.BuyersTable
import play.api.libs.json.Json
import com.github.kondaurovdev.jsonApi.fake.categories.CategoryTable
import com.github.kondaurovdev.jsonApi.field.iField
import com.github.kondaurovdev.jsonApi.table.relation.{Relation, iRelation}
import com.github.kondaurovdev.jsonApi.table.iTable

object ProductsTable extends iTable {

  object Relations {
    val category = Relation("category", Json.obj(
      "left.categoryId" -> "right.id"
    ), CategoryTable)
    val buyers = Relation("buyers", Json.obj(
      "left.id" -> "right.productId"
    ), BuyersTable, hasMany = true)
  }

  def tableName: String = "products"

  implicit def fields: List[iField] = List(
    ProductFields.id,
    ProductFields.model,
    ProductFields.vendor,
    ProductFields.categoryId
  )

  implicit def relations: List[iRelation] = List(
    Relations.category
  )

}
