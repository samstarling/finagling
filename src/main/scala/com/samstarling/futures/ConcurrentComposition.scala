package com.samstarling.futures

import java.util.concurrent.Executors

import com.twitter.util.{FuturePool, Future, Await}
import com.samstarling.util.time

object ConcurrentComposition extends App {

  val executor = Executors.newFixedThreadPool(2)
  val futurePool = FuturePool(executor)

  def slowTask(time: Int): Future[String] = futurePool {
    Thread.sleep(time)
    "Hello"
  }

  time("collect", {
    val f = Future.collect(List(slowTask(1000), slowTask(2000)))
    val result = Await.result(f)
    println(s"Result: $result")
  })

  time("join", {
    val f = Future.join(slowTask(1000), slowTask(2000))
    val result = Await.result(f)
    println(s"Result: $result")
  })

  /* Careful: this takes 3 seconds total */
  time("for", {
    val f = for {
      r1 <- slowTask(1000)
      r2 <- slowTask(2000)
    } yield (r1, r2)
    val result = Await.result(f)
    println(s"Result: $result")
  })

  time("for - v2", {
    val f1 = slowTask(1000)
    val f2 = slowTask(2000)
    val f = for {
      r1 <- f1
      r2 <- f2
    } yield (r1, r2)
    val result = Await.result(f)
    println(s"Result: $result")
  })

  executor.shutdown()
}