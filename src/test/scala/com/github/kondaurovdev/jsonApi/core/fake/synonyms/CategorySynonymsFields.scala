package com.github.kondaurovdev.jsonApi.core.fake.synonyms

import com.github.kondaurovdev.jsonApi.core.field.Field
import com.github.kondaurovdev.jsonApi.core.field.types.StrField
import com.github.kondaurovdev.jsonApi.core.field.types.number.NumberField

object CategorySynonymsFields {

  val id = Field("id", "id", NumberField.pk, hasDefault = true)
  val categoryId = Field("categoryId", "category_id", NumberField.pk)
  val synonym = Field("synonym", "synonym", StrField())

}
