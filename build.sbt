name := """shinest_batch"""

version := "1.0"

scalaVersion := "2.11.7"

// Change this to another test framework if you prefer
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"

// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"



libraryDependencies += "com.amazonaws" % "aws-java-sdk" % "1.10.27"

libraryDependencies += "com.typesafe" % "config" % "1.3.0"

libraryDependencies += "commons-io" % "commons-io" % "2.4"
