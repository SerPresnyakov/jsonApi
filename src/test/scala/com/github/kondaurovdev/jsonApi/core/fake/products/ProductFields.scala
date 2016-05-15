package com.github.kondaurovdev.jsonApi.core.fake.products

import com.github.kondaurovdev.jsonApi.core.field.Field
import com.github.kondaurovdev.jsonApi.core.field.types.StrField
import com.github.kondaurovdev.jsonApi.core.field.types.number.NumberField

object ProductFields {

  val id = Field("id", "id", NumberField.pk, hasDefault = true)
  val vendor = Field("vendor", "vendor", StrField())
  val model = Field("model", "model", StrField())
  val categoryId = Field("categoryId", "category_id", NumberField.pk)

}
