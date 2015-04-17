package com.samstarling.filters

import com.twitter.util.{Await, Future}

object Results extends App {

  type Urn = String
  type Url = String
  case class Track(urn: Urn, user: Urn, title: String)
  case class TrackWithAvatar(track: Track, avatar: Url)

  def getTrack(urn: Urn): Future[Track] =
    Future.value(Track(urn, "urn:users:123", "Foo Bar Baz"))

  def getAvatar(userUrn: Urn): Future[Url] =
    Future.value(s"https://artwork.com/users/$userUrn")

  def future = getTrack("urn:tracks:123")
  def failure = Future { throw new RuntimeException("Fails") }

  /* Deprecated, but it blocks */
  val t1: Track = future.get()
  println(s"future.get: $t1")

  /* This also blocks */
  val t2: Track = Await.result(future)
  println(s"Await.result: $t2")

  /* onSuccess is non-blocking */
  future onSuccess { track =>
    println(s"onSuccess: $track")
  }

  /* onFailure handles exceptions */
  failure onFailure { ex: Throwable =>
    println(s"onFailure: $ex")
  }

  /* Future.collect can bring a list together */
  Future.collect(List(future, future, future)) map {
    case x => println(s"Future.collect: $x")
  }

  /* You can be a bit more clever about it */
  Future.collect(List(future, future, future)) map {
    case x => println(s"Future.collect: $x")
  }

  /* But Future.join just returns Unit */
  Future.join(List(future)) map {
    case u => println(s"Future.join: $u")
  }

}
