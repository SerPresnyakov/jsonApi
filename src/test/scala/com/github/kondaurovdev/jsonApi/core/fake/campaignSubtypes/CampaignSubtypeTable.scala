package com.github.kondaurovdev.jsonApi.core.fake.campaignSubtypes

import com.github.kondaurovdev.jsonApi.core.dbs.iDbConn
import com.github.kondaurovdev.jsonApi.core.fake.TestDbConn
import com.github.kondaurovdev.jsonApi.core.field.iField
import com.github.kondaurovdev.jsonApi.core.table.iTableCrud
import com.github.kondaurovdev.jsonApi.core.table.relation.iRelation

object CampaignSubtypeTable extends iTableCrud {
  def tableName: String = "campaign_subtypes"
  def relations: List[iRelation] = List()
  def fields: List[iField] = List(
    CampaignSubtypeFields.id,
    CampaignSubtypeFields.name
  )
  def db: iDbConn = TestDbConn
}
