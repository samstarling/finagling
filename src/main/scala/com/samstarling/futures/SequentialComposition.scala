package com.samstarling.futures

import com.twitter.util.Future

object SequentialComposition extends App {

  def fetchUrl(url: String): Future[String] = Future { "Hello" }
  def findImageUrls(bytes: String): Seq[String] = List("A", "B", "C")

  val url = "http://www.google.com"

  val f: Future[String] = fetchUrl(url) flatMap { bytes =>
    val images = findImageUrls(bytes)
    if (images.isEmpty)
      Future.exception(new Exception("no image"))
    else
      fetchUrl(images(0))
  }

  f onSuccess { res =>
    println("Result: " + res)
  }
}
