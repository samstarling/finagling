package com.samstarling.servers

import java.net.InetSocketAddress

import com.samstarling.filters.{ExceptionHandlerFilter, TimeoutFilter, UnsafeParameterFilter}
import com.samstarling.services.MaybeSlowService
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.http.{Http, Request, RichHttp}
import com.twitter.finagle.util.DefaultTimer

object FiltersServer extends App {

  val timeoutFilter = new TimeoutFilter(DefaultTimer.twitter)
  val exceptionFilter = new ExceptionHandlerFilter()
  val unsafeParameterFilter = new UnsafeParameterFilter()

  val filters = exceptionFilter andThen timeoutFilter andThen unsafeParameterFilter
  val maybeSlowService = new MaybeSlowService()
  val service = filters andThen maybeSlowService

  val server = ServerBuilder()
    .codec(RichHttp[Request](Http()))
    .bindTo(new InetSocketAddress(8080))
    .name("HttpServer")
    .build(service)

}
