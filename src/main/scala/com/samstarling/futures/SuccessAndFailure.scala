package com.samstarling.futures

import com.twitter.util.Future

object SuccessAndFailure extends App {
  
  val willSucceed = Future { 3 }
  val willFail = Future { throw new RuntimeException("Uh oh") }
  
  willSucceed onSuccess { res =>
    println("Success! The result was: " + res)
  }
  
  willFail onFailure { cause: Throwable =>
    println("Fail! The cause was: " + cause)
  }
}
