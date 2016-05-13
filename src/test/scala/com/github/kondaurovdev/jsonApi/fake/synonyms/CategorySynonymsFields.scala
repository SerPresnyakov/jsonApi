package com.github.kondaurovdev.jsonApi.fake.synonyms

import com.github.kondaurovdev.jsonApi.field.Field
import com.github.kondaurovdev.jsonApi.field.types.StrField
import com.github.kondaurovdev.jsonApi.field.types.number.NumberField

object CategorySynonymsFields {

  val id = Field("id", "id", NumberField.pk, hasDefault = true)
  val categoryId = Field("categoryId", "category_id", NumberField.pk)
  val synonym = Field("synonym", "synonym", StrField())

}
