package com.xo.common.scala

object Test {

  def main(args: Array[String]): Unit = {
    var words = Set("hive", "hbase", "redis")
    val result = words.flatMap(x => x.toUpperCase)
    println(result)
  }

}
