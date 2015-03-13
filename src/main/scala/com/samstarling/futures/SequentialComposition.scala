package com.samstarling.futures

import com.twitter.util.Future

object SequentialComposition extends App {

  case class User(name: String)

  def authenticate(credentials: String) = Future { Some(User("sam")) }

  def getTweets(user: Option[User]) = Future { List("1", "2", "3") }

  // Here, the intermediate user object can't be accessed
  authenticate("foo").flatMap(getTweets) onSuccess { tweets =>
    println(s"Your tweets are: $tweets")
  }
  
  // Here, we retain access to a val for each step
  val future = for {
    user <- authenticate("foo")
    tweets <- getTweets(user)
  } yield (user, tweets)

  future onSuccess { case (user, tweets) =>
    println(s"The tweets for $user are: $tweets")
  }
}