package com.github.kondaurovdev.jsonApi.core.utils

import org.postgresql.util.PGobject

object PgHelper {

  def getPgObject(t: String, value: Any) = {
    val res = new PGobject()
    res.setType(t)
    res.setValue(value.toString)
    res
  }

}
