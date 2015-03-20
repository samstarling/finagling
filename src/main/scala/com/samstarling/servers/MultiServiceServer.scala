package com.samstarling.servers

import java.net.InetSocketAddress

import com.samstarling.filters.UnsafeParameterFilter
import com.samstarling.services.{EchoParametersService, HelloWorldService}
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.http.{Http, Request, RichHttp}
import org.jboss.netty.handler.codec.http.HttpMethod

object MultiServiceServer extends App {

  val hello = new HelloWorldService()
  val echo = new EchoParametersService()

  val filters = new UnsafeParameterFilter()

  val router = new Router(Map(
    Route(HttpMethod.GET, "/hello") -> hello,
    Route(HttpMethod.GET, "/world") -> hello,
    Route(HttpMethod.GET, "/debug") -> echo
  ))

  val service = filters andThen router

  val server = ServerBuilder()
    .codec(RichHttp[Request](Http()))
    .bindTo(new InetSocketAddress(8080))
    .name("HttpServer")
    .build(service)
}
