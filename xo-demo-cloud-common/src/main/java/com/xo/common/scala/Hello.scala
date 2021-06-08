package com.xo.common.scala

import java.awt.geom.Ellipse2D

import scala.collection.{immutable, mutable}
import scala.collection.mutable.{ArrayBuffer, Set}
import scala.io.StdIn // 可以在任何地方引入 可变集合

object Hello {


  def main(args: Array[String]) {

    val r = ArrayBuffer[Int]()
    r.append(1)
    val array = r.toArray


  }



  //scala 特质可以继承类（java不行），但是Test04的特质最后必须来源于同一父类Test01
  class Test01 {}
  trait Test02 extends Test01{}
  class Test03 extends Test01{}
  class Test04 extends Test03 with Test02 {}

  //java  类只能 extends class
  //scala  类能是 extends trait、class
  trait AA{}
  trait BB extends AA{}
  trait CC extends BB{}
  trait DD extends BB{}
  class EE {}
  class FF extends EE with CC with DD{}
//  class KK extends EE{}
}
