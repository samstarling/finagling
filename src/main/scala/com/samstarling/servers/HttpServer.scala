package com.samstarling.servers

import java.net.InetSocketAddress

import com.samstarling.services.HelloWorldService
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.http.{Request, RichHttp, Http}

object HttpServer extends App {

  val server = ServerBuilder()
    .codec(RichHttp[Request](Http()))
    .bindTo(new InetSocketAddress(8080))
    .name("HttpServer")
    .build(new HelloWorldService())
}

