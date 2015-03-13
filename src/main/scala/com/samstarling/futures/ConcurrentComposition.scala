package com.samstarling.futures

import com.samstarling.futures.clients._
import com.twitter.util.Future

object ConcurrentComposition extends App {

  val stringTasks = List(CommentsClient.getComments, AdvertsClient.getAdverts)
  val mixedTasks = List(CommentsClient.getComments, NumbersClient.getRandomNumber)

  /* collect takes a set of Futures of the same type */
  Future.collect[String](stringTasks) onSuccess { res =>
    println(s"Seq[String]: $res")
  }
  
  /* this will be Future[Seq[Any]] (which isn't great) */
  Future.collect[Any](mixedTasks) onSuccess { res =>
    println(s"Seq[Any]: $res")
  }

  /* but join takes a sequence of Futures whose types may be mixed */
  Future.join(mixedTasks) onSuccess { res =>
    println(s"Unit: $res")
  }
}



