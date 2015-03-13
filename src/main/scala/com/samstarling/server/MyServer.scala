package com.samstarling.server

import java.net.InetSocketAddress

import com.twitter.finagle.Service
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.http.Http
import com.twitter.util.Future
import org.jboss.netty.buffer.ChannelBuffers
import org.jboss.netty.handler.codec.http.{DefaultHttpResponse, HttpRequest, HttpResponse, HttpResponseStatus, HttpVersion}

object MyServer extends App {

  val rootService = new HelloWorldService()

  val address = new InetSocketAddress(10000)
  val server = ServerBuilder()
    .codec(Http())
    .bindTo(address)
    .name("HttpServer")
    .build(rootService)
}

class HelloWorldService extends Service[HttpRequest, HttpResponse] {
  override def apply(request: HttpRequest): Future[HttpResponse] = Future {
    val response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK)
    val content = ChannelBuffers.copiedBuffer("Hello world", "UTF-8")
    response.setContent(content)
    response
  }
}