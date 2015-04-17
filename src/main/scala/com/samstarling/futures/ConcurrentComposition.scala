package com.samstarling.futures

import com.twitter.util.{Await, Future}
import com.samstarling.util.time

object ConcurrentComposition extends App {

  def slowTask(time: Int): Future[String] = {
    Thread.sleep(time)
    Future.value("Hello")
  }

  /* collect takes ? seconds */
  time("Future.collect", {
    Await.result(Future.collect(List(slowTask(1000), slowTask(2000))))
  })

  /* join takes ? seconds */
  time("Future.join", {
    Await.result(Future.join(List(slowTask(1000), slowTask(2000))))
  })

  /* ? */
  time("Future.join flatMap", {
    Future.join(
      slowTask(1000),
      slowTask(2000)
    ).flatMap { case (comments, adverts) =>
      println(comments)
      println(adverts)
      Future.None
    }
  })
}
