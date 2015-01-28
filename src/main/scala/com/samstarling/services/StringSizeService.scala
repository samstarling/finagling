package com.samstarling.services

import com.twitter.finagle.Service
import com.twitter.util.Future

/* A service that counts string lengths */

class StringSizeService extends Service[String, Integer] {
  override def apply(request: String) = Future { request.size }
}

object StringSizeApp extends App {
  val stringSizer = new StringSizeService
  stringSizer("hello") onSuccess { res =>
    println("The result was: " + res)
  }
}