package com.github.kondaurovdev.jsonApi.core.fake.campaignSubtypes

import com.github.kondaurovdev.jsonApi.core.field.Field
import com.github.kondaurovdev.jsonApi.core.field.types.StrField
import com.github.kondaurovdev.jsonApi.core.field.types.number.NumberField

object CampaignSubtypeFields {

  val id = Field("id", "id", NumberField.pk, hasDefault = false)
  val name = Field("name", "name", StrField())

}
