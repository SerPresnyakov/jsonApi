package com.github.kondaurovdev.jsonApi.table.methods

object RowHelper {

  def groupForBatch(rows: Iterable[Seq[(Symbol, Any)]]): Seq[Seq[Seq[(Symbol, Any)]]] = {

    rows.toSeq.groupBy(row => {
      val sorted = row.sortBy(_._1.name)
      sorted.map(_._1.name).mkString("_")
    })
      .map(t => t._1 -> t._2)
      .toList.sortBy(_._1)
      .map(_._2)
      .map(_.filter(_.nonEmpty))
      .filter(_.nonEmpty)

  }

}
