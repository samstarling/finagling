package com.samstarling.servers

import java.net.InetSocketAddress

import com.samstarling.filters.{ExceptionHandlerFilter, TimeoutFilter}
import com.samstarling.services.MaybeSlowService
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.http.{Http, Request, RichHttp}
import com.twitter.finagle.util.DefaultTimer
import com.twitter.util.Duration

object TimeoutServer extends App {

  val timer = DefaultTimer.twitter

  val timeoutFilter = new TimeoutFilter(timer)
  val exceptionFilter = new ExceptionHandlerFilter()

  val filters = exceptionFilter andThen timeoutFilter
  val maybeSlowService = new MaybeSlowService()
  val service = filters andThen maybeSlowService

  val server = ServerBuilder()
    .codec(RichHttp[Request](Http()))
    .bindTo(new InetSocketAddress(10000))
    .name("HttpServer")
    .build(service)

}
