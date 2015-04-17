package com.samstarling

package object util {
  def time[T](name: String, block: => T): T = {
    println(s"Start $name")
    val start = System.currentTimeMillis()
    val result = block
    val elapsed = System.currentTimeMillis() - start
    println(s"End $name: $elapsed ms")
    result
  }
}
