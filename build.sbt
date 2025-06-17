val scala3Version = "3.7.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "fantome",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "1.0.0" % Test,
      "org.scala-lang" %% "toolkit" % "0.7.0",
      "com.softwaremill.sttp.tapir" %% "tapir-core" % "1.11.34",
      "com.softwaremill.sttp.tapir" %% "tapir-zio" % "1.11.34",
      "com.softwaremill.sttp.tapir" %% "tapir-zio-http-server" % "1.11.34",
      "dev.zio" %% "zio-schema-json"     % "1.7.2",
      "dev.zio" %% "zio-json" % "0.7.43",
      "dev.zio" %% "zio-schema-avro" % "1.7.2"
    )
  )