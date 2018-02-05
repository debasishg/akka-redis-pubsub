import sbt._
import sbt.Keys._

object AkkaPubsubProject extends Build
{
  import Resolvers._

  lazy val root = Project("AkkaRedisPubsub", file(".")) settings(coreSettings : _*)

  lazy val commonSettings: Seq[Setting[_]] = Seq(
    organization := "net.debasishg",
    version := "0.0.2",
    scalaVersion := "2.11.12",
    crossScalaVersions := Seq("2.12.4", "2.11.12"),

    scalacOptions in Compile ++= Seq( "-unchecked", "-feature", "-language:postfixOps", "-deprecation" ),

    resolvers ++= Seq(akkaRepo)
  )

  lazy val coreSettings = commonSettings ++ Seq(
    name := "AkkaRedisPubsub",
    libraryDependencies := Seq(
      "net.debasishg"     %% "redisclient"             % "3.4",
      "junit"             %  "junit"                   % "4.12"       % "test",
      "org.scalatest"     %%  "scalatest"              % "3.0.4"      % "test"),

    libraryDependencies += {
      if(scalaVersion.value.startsWith("2.12"))
        "com.typesafe.akka" %% "akka-actor" % "2.5.9"
      else
        "com.typesafe.akka" %% "akka-actor" % "2.4.20"
    },
    parallelExecution in Test := false,
    publishTo <<= version { (v: String) =>
      val nexus = "https://oss.sonatype.org/"
      if (v.trim.endsWith("SNAPSHOT")) Some("snapshots" at nexus + "content/repositories/snapshots")
      else Some("releases" at nexus + "service/local/staging/deploy/maven2")
    },
    credentials += Credentials(Path.userHome / ".sbt" / "sonatype.credentials"),
    publishMavenStyle := true,
    publishArtifact in Test := false,
    pomIncludeRepository := { repo => false },
    pomExtra := <url>https://github.com/debasishg/scala-redis</url>
        <licenses>
          <license>
            <name>Apache 2.0 License</name>
            <url>http://www.apache.org/licenses/LICENSE-2.0.html</url>
            <distribution>repo</distribution>
          </license>
        </licenses>
        <scm>
          <url>git@github.com:debasishg/akka-redis-pubsub.git</url>
          <connection>scm:git:git@github.com:debasishg/akka-redis-pubsub.git</connection>
        </scm>
        <developers>
          <developer>
            <id>debasishg</id>
            <name>Debasish Ghosh</name>
            <url>http://debasishg.blogspot.com</url>
          </developer>
        </developers>,
    unmanagedResources in Compile <+= baseDirectory map { _ / "LICENSE" }
  )
}

object Resolvers {
  val akkaRepo = "typesafe repo" at "http://repo.typesafe.com/typesafe/releases/"
}
