package com.samstarling.services

import com.twitter.finagle.Service
import com.twitter.util.Future

/* A service that counts string lengths */

class BasicService extends Service[String, Integer] {
  override def apply(request: String): Future[Integer] =
    Future { request.size }
}

object BasicServiceApp extends App {
  val service = new BasicService
  service("hello") onSuccess { res =>
    println("The result was: " + res)
  }
}