package com.samstarling.futures

import com.samstarling.futures.clients.{CommentsClient, AdvertsClient}
import com.twitter.util.Future

object ConcurrentComposition extends App {

  val tasks = List(CommentsClient.getComments, AdvertsClient.getAdverts)
  val future = Future.collect(tasks)
  
  future onSuccess { res =>
    println(res)
  }
}



