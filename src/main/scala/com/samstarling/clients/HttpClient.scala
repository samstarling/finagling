package com.samstarling.clients

import org.jboss.netty.handler.codec.http.{DefaultHttpRequest, HttpRequest, HttpResponse, HttpVersion, HttpMethod}
import com.twitter.finagle.Service
import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.http.Http

object HttpClient extends App {

  val client: Service[HttpRequest, HttpResponse] = ClientBuilder()
    .codec(Http())
    .hosts("google.com:80")
    .hostConnectionLimit(1)
    .build()

  val req = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/")

  client(req) onSuccess { res =>
    println("got response", res)
  } onFailure { exc =>
    println("failed :-(", exc)
  }
}
