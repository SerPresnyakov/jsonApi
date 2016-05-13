package com.github.kondaurovdev.jsonApi.utils

import org.specs2.mutable.Specification

class HelperSpec extends Specification {

  "Helper" should {

    "camelCase2SnakeCase test" in {

      Helper.cameCaseToSnakeCase("accountId") must beEqualTo("account_id")
      Helper.cameCaseToSnakeCase("mySuperColumn") must beEqualTo("my_super_column")

    }

    "snakeCase2CamelCase test" in {

      Helper.snakeCaseToCamelCase("account_id") must beEqualTo("accountId")
      Helper.snakeCaseToCamelCase("my_super_column") must beEqualTo("mySuperColumn")

    }

    "clearStr" in {

      "case 1" in {

        Helper.clearStr("\r\nBla \r\n equal\n\r1") must beEqualTo("Bla equal 1")

      }

    }

  }

}
