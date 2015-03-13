package com.samstarling.futures

import com.twitter.util.Future

object ConcurrentComposition extends App {

  val tasks = List(CommentsClient.getComments, AdvertsClient.getAdverts)
  val future = Future.collect(tasks)
  
  future onSuccess { res =>
    println(res)
  }
}

object CommentsClient {
  def getComments: Future[String] = Future {
    "comment"
  }
}

object AdvertsClient {
  def getAdverts: Future[String] = Future {
    "adverts"
  }
}