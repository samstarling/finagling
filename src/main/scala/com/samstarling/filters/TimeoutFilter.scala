package com.samstarling.filters

import com.twitter.finagle._
import com.twitter.finagle.http.{Response, Request}
import com.twitter.util.{Duration, Future, Timer}

class TimeoutFilter(timer: Timer) extends SimpleFilter[Request, Response] {

  val timeout = Duration.fromSeconds(1)

  def apply(request: Request, service: Service[Request, Response]): Future[Response] = {
    val res = service(request)
    res.within(timer, timeout)
  }
}