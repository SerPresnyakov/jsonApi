package com.github.kondaurovdev.jsonApi.fake.campaignSubtypes

import com.github.kondaurovdev.jsonApi.dbs.iDbConn
import com.github.kondaurovdev.jsonApi.fake.TestDbConn
import com.github.kondaurovdev.jsonApi.field.iField
import com.github.kondaurovdev.jsonApi.table.iTableCrud
import com.github.kondaurovdev.jsonApi.table.relation.iRelation

object CampaignSubtypeTable extends iTableCrud {
  def tableName: String = "campaign_subtypes"
  def relations: List[iRelation] = List()
  def fields: List[iField] = List(
    CampaignSubtypeFields.id,
    CampaignSubtypeFields.name
  )
  def db: iDbConn = TestDbConn
}
