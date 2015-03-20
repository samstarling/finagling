package com.samstarling.services

import com.twitter.finagle.Service
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.Future
import org.jboss.netty.handler.codec.http._

class EchoParametersService extends Service[Request, Response] {
  override def apply(request: Request): Future[Response] = Future {
    val response = Response(HttpVersion.HTTP_1_1, HttpResponseStatus.OK)
    response.setContentString(request.params.mkString(", "))
    response
  }
}
