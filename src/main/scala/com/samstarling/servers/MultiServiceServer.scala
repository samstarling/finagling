package com.samstarling.servers

import java.net.InetSocketAddress

import com.samstarling.services.HelloWorldService
import com.twitter.finagle.Service
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.http.{Http, Request, Response, RichHttp}
import com.twitter.util.Future
import org.jboss.netty.handler.codec.http.{HttpResponseStatus, HttpVersion}

object MultiServiceServer extends App {

  val hello: Service[Request, Response] = new HelloWorldService()

  val service = new Router(Map("/hello" -> hello))

  val server = ServerBuilder()
    .codec(RichHttp[Request](Http()))
    .bindTo(new InetSocketAddress(8080))
    .name("HttpServer")
    .build(service)

}

class Router(services: Map[String, Service[Request, Response]]) extends Service[Request, Response] {
  override def apply(request: Request): Future[Response] = {
    services.get(request.getUri()) match {
      case Some(service) => service.apply(request)
      case None => Future.value(Response(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND))
    }
  }
}