package com.samstarling.servers

import com.twitter.finagle.Service
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.Future
import org.jboss.netty.handler.codec.http.{HttpResponseStatus, HttpVersion}

class Router(services: Map[String, Service[Request, Response]]) extends Service[Request, Response] {
  override def apply(request: Request): Future[Response] = {
    services.get(request.getUri()) match {
      case Some(service) => service.apply(request)
      case None => notFound(request)
    }
  }

  private def notFound(request: Request) = Future {
    val response = Response(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND)
    response.setContentString(s"No handler found for path: ${request.getUri()}")
    response
  }
}
