package com.samstarling.servers

import java.net.InetSocketAddress

import com.samstarling.filters.UnsafeParameterFilter
import com.samstarling.services.HelloWorldService
import com.twitter.finagle.Service
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.http.{Http, Request, Response, RichHttp}
import org.jboss.netty.handler.codec.http.HttpMethod

object MultiServiceServer extends App {

  val hello: Service[Request, Response] = new HelloWorldService()

  val filters = new UnsafeParameterFilter()

  val router = new Router(Map(
    Route(HttpMethod.GET, "/hello") -> hello,
    Route(HttpMethod.GET, "/world") -> hello
  ))

  val service = filters andThen router

  val server = ServerBuilder()
    .codec(RichHttp[Request](Http()))
    .bindTo(new InetSocketAddress(8080))
    .name("HttpServer")
    .build(service)
}
