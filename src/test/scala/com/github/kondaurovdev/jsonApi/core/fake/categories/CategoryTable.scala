package com.github.kondaurovdev.jsonApi.core.fake.categories

import com.github.kondaurovdev.jsonApi.core.fake.synonyms.CategorySynonymsTable
import play.api.libs.json.Json
import com.github.kondaurovdev.jsonApi.core.dbs.iDbConn
import com.github.kondaurovdev.jsonApi.core.fake.campaignSubtypes.CampaignSubtypeTable
import com.github.kondaurovdev.jsonApi.core.fake.TestDbConn
import com.github.kondaurovdev.jsonApi.core.field.iField
import com.github.kondaurovdev.jsonApi.core.table.relation.{iRelation, Relation}
import com.github.kondaurovdev.jsonApi.core.table.iTableCrud

object CategoryTable extends iTableCrud {
  def tableName: String = "categories"

  def relations: List[iRelation] = List(
    Relation("campaignSubtype", Json.obj(
      "left.campaignSubtypeId" -> "right.id"
    ), CampaignSubtypeTable),
    Relation("synonyms", Json.obj(
      "left.id" -> "right.categoryId"
    ), CategorySynonymsTable, hasMany = true)
  )

  def fields: List[iField] = List(
    CategoryFields.id,
    CategoryFields.name,
    CategoryFields.campaignSubtypeId
  )

  def db: iDbConn = TestDbConn
}
