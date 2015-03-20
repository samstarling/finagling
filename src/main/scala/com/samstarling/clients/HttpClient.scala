package com.samstarling.clients

import com.twitter.finagle.Service
import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.http.Http
import org.jboss.netty.handler.codec.http.{DefaultHttpRequest, HttpMethod, HttpRequest, HttpResponse, HttpVersion}

object HttpClient extends App {

  val client: Service[HttpRequest, HttpResponse] = ClientBuilder()
    .codec(Http())
    .hosts("google.com:80")
    .hostConnectionLimit(1)
    .build()

  val req = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/")

  client(req) onSuccess { response =>
    println(response)
  } onFailure { ex =>
    println("Fail!", ex)
  }
}
