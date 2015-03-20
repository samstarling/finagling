package com.samstarling.servers

import java.net.InetSocketAddress

import com.samstarling.services.HelloWorldService
import com.twitter.finagle.Service
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.http.{Http, Request, Response, RichHttp}
import org.jboss.netty.handler.codec.http.HttpMethod

object MultiServiceServer extends App {

  val hello: Service[Request, Response] = new HelloWorldService()

  val service = new Router(Map(
    Route(HttpMethod.GET, "/hello") -> hello,
    Route(HttpMethod.GET, "/world") -> hello
  ))

  val server = ServerBuilder()
    .codec(RichHttp[Request](Http()))
    .bindTo(new InetSocketAddress(8080))
    .name("HttpServer")
    .build(service)

}

