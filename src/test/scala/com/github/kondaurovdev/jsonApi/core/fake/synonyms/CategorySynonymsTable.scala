package com.github.kondaurovdev.jsonApi.core.fake.synonyms

import com.github.kondaurovdev.jsonApi.core.fake.categories.CategoryTable
import com.github.kondaurovdev.jsonApi.core.field.iField
import com.github.kondaurovdev.jsonApi.core.table.iTable
import com.github.kondaurovdev.jsonApi.core.table.relation.{Relation, iRelation}
import play.api.libs.json.Json

object CategorySynonymsTable extends iTable {
  def tableName: String = "categorySynonyms"
  def relations: List[iRelation] = List(
    Relation("category", Json.obj("left.categoryId" -> "right.id"), CategoryTable)
  )
  def fields: List[iField] = List(
    CategorySynonymsFields.id,
    CategorySynonymsFields.synonym,
    CategorySynonymsFields.categoryId
  )
}
