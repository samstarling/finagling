package com.samstarling.basics

object Map extends App {

  // map
  def timesTwo(number: Int): Int = number * 2
  val list = List(10, 20, 30)
  println(list.map(timesTwo))

  // flatMap
  def lookupScores(playerID: Int): List[Int] = List(playerID, playerID * 2, playerID * 3)
  println("map", List(1, 2, 3).map(lookupScores))
  println("flatMap", List(1, 2, 3).flatMap(lookupScores))
}
