package com.samstarling.filters

import com.twitter.finagle._
import com.twitter.finagle.http.{Response, Request}
import com.twitter.util.{Duration, Future, Timer}

class TimeoutFilter(timeout: Duration, exception: RequestTimeoutException, timer: Timer) extends SimpleFilter[Request, Response] {
  def this(timeout: Duration, timer: Timer) =
    this(timeout, new IndividualRequestTimeoutException(timeout), timer)

  def apply(request: Request, service: Service[Request, Response]): Future[Response] = {
    val res = service(request)
    res.within(timer, timeout) rescue {
      case _ =>
        res.raise(exception)
        Future.exception(exception)
    }
  }
}