package com.github.kondaurovdev.jsonApi.core

import org.postgresql.util.PGobject

package object table {

  val fromAlias = "base"

  val aggAlias = "agg"

  type bindings = Seq[(Symbol, PGobject)]

}
