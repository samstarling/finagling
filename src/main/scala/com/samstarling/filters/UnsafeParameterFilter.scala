package com.samstarling.filters

import com.twitter.finagle._
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.Future

class UnsafeParameterFilter extends SimpleFilter[Request, Response] {

  def apply(request: Request, service: Service[Request, Response]): Future[Response] = {
    val fooParams = request.getParams("foo")
    if(!fooParams.isEmpty) {
      throw new RuntimeException("Foo parameter!")
    } else {
      service(request)
    }
  }
}