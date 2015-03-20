package com.samstarling.services

import com.twitter.finagle.Service
import com.twitter.util.Future
import org.jboss.netty.buffer.ChannelBuffers
import org.jboss.netty.handler.codec.http._

/**
 * Created by samuelstarling on 20/03/15.
 */
class HelloWorldService extends Service[HttpRequest, HttpResponse] {
  override def apply(request: HttpRequest): Future[HttpResponse] = Future {
    val response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK)
    val content = ChannelBuffers.copiedBuffer("Hello world", "UTF-8")
    response.setContent(content)
    response
  }
}
