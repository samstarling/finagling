package com.samstarling.filters

import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.{Service, SimpleFilter}
import com.twitter.util.Future
import org.jboss.netty.handler.codec.http.{HttpResponseStatus, HttpVersion}

class ExceptionHandlerFilter extends SimpleFilter[Request, Response] {
  def apply(request: Request, service: Service[Request, Response]): Future[Response] = {
    service(request).handle(handleThrowable)
  }

  private def handleThrowable: PartialFunction[Throwable, Response] = {
    case ex: Throwable =>
      val response = Response(HttpVersion.HTTP_1_1, HttpResponseStatus.INTERNAL_SERVER_ERROR)
      response.setContentString(s"Uh oh: $ex")
      response
  }
}
