package com.samstarling.filters

import com.twitter.finagle._
import com.twitter.finagle.SimpleFilter
import com.twitter.finagle.http.{Response, Request}
import com.twitter.util.{Duration, Future, Timer}

class TimeoutFilter(timer: Timer) extends SimpleFilter[Request, Response] {

  val timeout = Duration.fromMilliseconds(500)

  def apply(request: Request, service: Service[Request, Response]): Future[Response] = {
    val res = service(request)
    res.within(timer, timeout) rescue {
      case ex => println("Timeout"); Future.exception(ex)
    }
  }
}