package com.github.kondaurovdev.jsonApi.core.table

import org.specs2.mutable.Specification
import play.api.libs.json.Json
import com.github.kondaurovdev.jsonApi.core.fake.products.ProductsTable
import com.github.kondaurovdev.jsonApi.core.field.types.StrField
import com.github.kondaurovdev.jsonApi.core.field.types.number.NumberField
import com.github.kondaurovdev.jsonApi.core.utils.JsonSchema

class validatorSpec extends Specification {

  val productProps = Json.obj(
    "id" -> NumberField.getSchema,
    "model" -> StrField.getSchema,
    "vendor" -> StrField.getSchema,
    "categoryId" -> NumberField.getSchema
  )

  "Validator test" should {

    "getSchema, case 1" in {

      validator.getSchema(JsonSchema.getObj(
        "id" -> NumberField.getSchema,
        "model" -> StrField.getSchema
      )("id", "model")).isRight must beTrue

    }

    "forInsertSchema" in {

      ProductsTable.forInsertSchema.right.map(s => Json.toJson(s)) must beRight(
        Json.obj(
          "type" -> "object",
          "properties" -> productProps,
          "required" -> List("model", "vendor", "categoryId")
        )
      )

    }

    "getSchema for update" in {

      ProductsTable.forUpdateSchema.right.map(s => Json.toJson(s)) must beRight(
        Json.obj(
          "type" -> "object",
          "properties" -> productProps,
          "required" -> List("id")
        )
      )

    }

  }

}
