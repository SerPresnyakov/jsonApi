package com.github.kondaurovdev.jsonApi.table.methods

import org.specs2.mutable.Specification

class RowHelperSpec extends Specification {

  "RowHelper" should {

    "groupForBatch" in {

      RowHelper.groupForBatch(Seq(
      Seq(
        'a -> 1,
        'b -> 2
      ), Seq(
        'a -> 3,
        'b -> 4
      ), Seq(
        'a -> 1,
        'b -> 2,
        'c -> 3
      ), Seq(
        'a -> 1,
        'c -> 2
      ))) must beEqualTo(Seq(
        Seq(
          Seq(
            'a -> 1,
            'b -> 2
          ),
          Seq(
            'a -> 3,
            'b -> 4
          )
        ),
        Seq(
          Seq(
            'a -> 1,
            'b -> 2,
            'c -> 3
          )
        ),
        Seq(
          Seq(
            'a -> 1,
            'c -> 2
          )
        )
      ))

    }

  }



}
