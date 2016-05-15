package com.github.kondaurovdev.jsonApi.core.table.methods.insert

import org.specs2.mutable.Specification

class InsertSpec extends Specification {

  "Insert" should {

    "getColumns" in {

      getColumns(
        'b -> 1,
        'a -> 1,
        'c -> 1
      ) must beEqualTo(Seq("a", "b", "c") -> Seq('a -> 1, 'b -> 1, 'c -> 1))

    }

  }

}
