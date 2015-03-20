package com.samstarling.servers

import java.net.InetSocketAddress

import com.samstarling.services.HelloWorldService
import com.twitter.finagle.Service
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.http.Http
import com.twitter.util.Future
import org.jboss.netty.buffer.ChannelBuffers
import org.jboss.netty.handler.codec.http.{DefaultHttpResponse, HttpRequest, HttpResponse, HttpResponseStatus, HttpVersion}

object HttpServer extends App {

  val server = ServerBuilder()
    .codec(Http())
    .bindTo(new InetSocketAddress(10000))
    .name("HttpServer")
    .build(new HelloWorldService())
}

