package com.github.kondaurovdev.jsonApi.core.table.query

import com.github.kondaurovdev.jsonApi.core.fake.synonyms.CategorySynonymsTable
import org.specs2.mutable.Specification
import play.api.libs.json.Json
import com.github.kondaurovdev.jsonApi.core.fake.products.ProductsTable

class MapperSpec extends Specification {

  "Mapper" should {

    "mapRow" in {
      "case 1" in {
        Mapper.mapRow(Json.obj(
          "base" -> Json.obj(
            "id" -> 1,
            "model" -> "superCar",
            "vendor" -> "bmw",
            "category_id" -> 1
          ),
          "base_category" -> Json.obj(
            "id" -> 1,
            "category_name" -> "cars",
            "campaign_subtype_id" -> 123
          ),
          "base_category_campaignsubtype" -> Json.obj(
            "id" -> 123,
            "name" -> "ourCars"
          ),
          "base_category_synonyms" -> Json.arr(
            Json.obj(
              "id" -> 1,
              "category_id" -> 1,
              "synonym" -> "bla"
            ),
            Json.obj(
              "id" -> 2,
              "category_id" -> 1,
              "synonym" -> "blabla"
            )
          )
        ), "base", ProductsTable) must beRight(Json.obj(
          "id" -> 1,
          "model" -> "superCar",
          "vendor" -> "bmw",
          "categoryId" -> 1,
          "category" -> Json.obj(
            "id" -> 1,
            "name" -> "cars",
            "campaignSubtypeId" -> 123,
            "campaignSubtype" -> Json.obj(
              "id" -> 123,
              "name" -> "ourCars"
            ),
            "synonyms" -> List(
              Json.obj(
                "id" -> 1,
                "synonym" -> "bla",
                "categoryId" -> 1
              ),
              Json.obj(
                "id" -> 2,
                "synonym" -> "blabla",
                "categoryId" -> 1
              )
            )
          )
        ))
      }
    }
  }

}
