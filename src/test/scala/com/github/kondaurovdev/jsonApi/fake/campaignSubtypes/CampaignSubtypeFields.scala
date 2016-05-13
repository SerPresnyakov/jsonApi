package com.github.kondaurovdev.jsonApi.fake.campaignSubtypes

import com.github.kondaurovdev.jsonApi.field.Field
import com.github.kondaurovdev.jsonApi.field.types.StrField
import com.github.kondaurovdev.jsonApi.field.types.number.NumberField

object CampaignSubtypeFields {

  val id = Field("id", "id", NumberField.pk, hasDefault = false)
  val name = Field("name", "name", StrField())

}
