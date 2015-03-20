package com.samstarling.services

import com.twitter.finagle.Service
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.Future
import org.jboss.netty.handler.codec.http._

import scala.util.Random

class MaybeSlowService extends Service[Request, Response] {
  override def apply(request: Request): Future[Response] = Future {
    val delay = Random.nextInt(2000)
    Thread.sleep(delay)
    val response = Response(HttpVersion.HTTP_1_1, HttpResponseStatus.INTERNAL_SERVER_ERROR)
    response.setContentString(s"That took $delay ms")
    response
  }
}
