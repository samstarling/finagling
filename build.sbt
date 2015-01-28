name := "Finagle Playground"

organization := "com.samstarling"

version := "0.0.1"

scalaVersion := "2.10.3"

libraryDependencies ++= Seq(
  "com.twitter" %% "finagle-http" % "6.24.0",
  "org.scalatest" % "scalatest_2.10" % "2.0" % "test" withSources() withJavadoc(),
  "org.scalacheck" %% "scalacheck" % "1.10.0" % "test" withSources() withJavadoc()
)

initialCommands := "import com.samstarling._"

