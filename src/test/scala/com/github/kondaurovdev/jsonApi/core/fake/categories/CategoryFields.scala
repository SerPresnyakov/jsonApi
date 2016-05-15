package com.github.kondaurovdev.jsonApi.core.fake.categories

import com.github.kondaurovdev.jsonApi.core.field.Field
import com.github.kondaurovdev.jsonApi.core.field.types.StrField
import com.github.kondaurovdev.jsonApi.core.field.types.number.NumberField

object CategoryFields {

  val id = Field("id", "id", NumberField.pk, hasDefault = false)
  val campaignSubtypeId = Field("campaignSubtypeId", "campaign_subtype_id", NumberField.pk)
  val name = Field("name", "category_name", StrField())

}
