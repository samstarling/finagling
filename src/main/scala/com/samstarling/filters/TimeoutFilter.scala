package com.samstarling.filters

import com.twitter.finagle._
import com.twitter.finagle.http.{Response, Request}
import com.twitter.util.{Duration, Future, Timer}

class TimeoutFilter(timeout: Duration, timer: Timer) extends SimpleFilter[Request, Response] {
  def apply(request: Request, service: Service[Request, Response]): Future[Response] = {
    val res = service(request)
    res.within(timer, timeout) rescue {
      case ex =>
        res.raise(ex)
        Future.exception(ex)
    }
  }
}