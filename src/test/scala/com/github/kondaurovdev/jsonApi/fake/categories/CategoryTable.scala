package com.github.kondaurovdev.jsonApi.fake.categories

import com.github.kondaurovdev.jsonApi.fake.synonyms.CategorySynonymsTable
import play.api.libs.json.Json
import com.github.kondaurovdev.jsonApi.dbs.iDbConn
import com.github.kondaurovdev.jsonApi.fake.campaignSubtypes.CampaignSubtypeTable
import com.github.kondaurovdev.jsonApi.fake.TestDbConn
import com.github.kondaurovdev.jsonApi.field.iField
import com.github.kondaurovdev.jsonApi.table.relation.{iRelation, Relation}
import com.github.kondaurovdev.jsonApi.table.iTableCrud

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
