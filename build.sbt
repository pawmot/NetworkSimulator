name := "networkSimulator"

version := "0.0.1"

scalaVersion := "2.11.7"

// Akka
val typesafeAkkaRepository = "com.typesafe.akka"
val akkaVersion = "2.4.0-RC2"
val modules = Seq(
  ("akka-actor", None),
  ("akka-testkit", Some("test"))
)

libraryDependencies ++= modules.map {
  case (mod, None) => typesafeAkkaRepository %% mod % akkaVersion
  case (mod, Some(conf)) => typesafeAkkaRepository %% mod % akkaVersion % conf
}

// ScalaTest
libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"