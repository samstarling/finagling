package com.samstarling.futures.clients

import com.twitter.util.Future

import scala.util.Random

object NumbersClient {
  
  val generator = new Random
  
  def getRandomNumber: Future[Int] = Future {
    generator.nextInt(1000)
  }
}
