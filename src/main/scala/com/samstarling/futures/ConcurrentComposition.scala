package com.samstarling.futures

import com.twitter.util.Future

object ConcurrentComposition extends App {

  def fetchTrack(): Future[String] =
    Future { "My Track" }
  
  def findComments(track: String): Future[Seq[String]] =
    Future { List("Nice!", "Awesome!", "Terrible!") }
  
  def sanitizeComment(comment: String): Future[String] =
    Future { comment.toLowerCase }

  val f: Future[Seq[String]] =
    fetchTrack() flatMap { content =>
      findComments(content).flatMap { comments =>
        Future.collect(comments.map { sanitizeComment })
      }
    }

  f onSuccess { res =>
    println("Result: " + res)
  }
}
