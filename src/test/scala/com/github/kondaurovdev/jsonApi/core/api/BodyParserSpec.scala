package com.github.kondaurovdev.jsonApi.core.api

import org.specs2.mutable.Specification
import play.api.libs.json.Json
import com.github.kondaurovdev.jsonApi.core.fake.products.ProductsTable
import com.github.kondaurovdev.jsonApi.core.field.types.StrField
import com.github.kondaurovdev.jsonApi.core.field.types.number.NumNorm

class BodyParserSpec extends Specification {

  "BodyParser" should {

    "parseObj" in {

      "case 1" in {

        BodyParser(ProductsTable, forUpdate = false).parseObj(Json.obj(
          "id" -> 1,
          "model" -> "c3po",
          "vendor" -> "lucas",
          "categoryId" -> 123
        )) must beRight(Seq(
          'id -> NumNorm.getPgObject(1),
          'model -> StrField.getPgObject("c3po"),
          'vendor -> StrField.getPgObject("lucas"),
          'category_id -> NumNorm.getPgObject(123)
        ))

      }

    }

  }

}
