package com.github.kondaurovdev.jsonApi

import org.postgresql.util.PGobject

package object table {

  val fromAlias = "base"

  val aggAlias = "agg"

  type bindings = Seq[(Symbol, PGobject)]

}
