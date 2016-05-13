package com.github.kondaurovdev.jsonApi.fake.categories

import com.github.kondaurovdev.jsonApi.field.Field
import com.github.kondaurovdev.jsonApi.field.types.StrField
import com.github.kondaurovdev.jsonApi.field.types.number.NumberField

object CategoryFields {

  val id = Field("id", "id", NumberField.pk, hasDefault = false)
  val campaignSubtypeId = Field("campaignSubtypeId", "campaign_subtype_id", NumberField.pk)
  val name = Field("name", "category_name", StrField())

}
