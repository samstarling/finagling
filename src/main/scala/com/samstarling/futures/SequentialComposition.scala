package com.samstarling.futures

import com.twitter.util.Future

object SequentialComposition extends App {

  case class User(name: String)

  def authenticate(credentials: String) = Future { 
    if (credentials == "correct") User(credentials)
    else throw new RuntimeException("Invalid credentials")
  }

  def getTweets(user: User) = Future {
    List("lol", "rofl", "lmao")
  }

  // Here, the intermediate user object can't be accessed
  authenticate("foo").flatMap(getTweets) onSuccess { tweets =>
    println(s"Your tweets are: $tweets")
  }

  // Here, we retain access to a val for each step
  val future = for {
    user <- authenticate("correct")
    tweets <- getTweets(user)
  } yield (user, tweets)

  future onSuccess { case (user, tweets) =>
    println(s"The tweets for $user are: $tweets")
  } onFailure { ex =>
    println(s"Failure: $ex")
  }
}