package com.samstarling

import com.samstarling.services.StringSizeService
import com.twitter.finagle.Service


package object filters {
  val service = new StringSizeService
  
  // val timeoutFilter = new TimeoutFilter[HttpRequest, HttpResponse](...)
  // val serviceWithTimeout: Service[HttRequest, HttpResponse] =
  // timeoutFilter andThen
}
