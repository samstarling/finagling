package com.samstarling.futures.clients

import com.twitter.util.Future

/**
 * Created by samuelstarling on 13/03/15.
 */
object AdvertsClient {
  def getAdverts: Future[String] = Future {
    "adverts"
  }
}
