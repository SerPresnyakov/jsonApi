package com.github.kondaurovdev.jsonApi.table.relation

import org.specs2.mutable.Specification
import com.github.kondaurovdev.jsonApi.fake.categories.{CategoryFields, CategoryTable}
import com.github.kondaurovdev.jsonApi.fake.products.{ProductFields, ProductsTable}

class RelationSpec extends Specification {

  "Relation" should {

    "regex test" in {
      leftRightRegex.findFirstIn("RIGHT.id").isDefined must beTrue
    }

    "extractField" in {

      "case 1" in {
        extractField("left.id", ProductsTable, CategoryTable) must beRight(
          ProductsTable -> ProductFields.id
        )
      }

      "case 2" in {
        extractField("left123.id", ProductsTable, CategoryTable).isLeft must beTrue
      }

      "case 3" in {
        extractField("right.id", ProductsTable, CategoryTable) must beRight(
          CategoryTable -> CategoryFields.id
        )
      }

    }

    "getOn test" in {

      "case 1" in {

        ProductsTable.Relations.category.getOnClause(ProductsTable, "base") must beRight(List(
          "base.category_id" -> "base_category.id"
        ), List())

      }

    }

    "getOnColumns test" in {

      "case 1" in {

        ProductsTable.Relations.category.getOnFields("right", ProductsTable) must beRight(List(
          CategoryFields.id
        ))

      }

    }

  }

}
