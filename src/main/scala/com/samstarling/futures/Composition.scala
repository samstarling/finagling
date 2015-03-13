package com.samstarling.futures

import com.twitter.util.Future


object Composition extends App {

  case class User(id: String)
  
  def authenticate(credential: String): Future[Option[User]] = Future {
    Some(User("foo"))
  }
}
