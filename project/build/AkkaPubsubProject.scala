import sbt._

class AkkaPubsubProject(info: ProjectInfo) extends DefaultProject(info) with AkkaBaseProject 
{
  val scalaToolsSnapshots = "Scala-Tools Maven2 Snapshots Repository" at "http://scala-tools.org/repo-snapshots"
  val scalaToolsReleases = "Scala-Tools Maven2 Releases Repository" at "http://scala-tools.org/repo-releases"
  val akkaRepo = "Akka Repository" at "http://akka.io/repository"

  val akkaActor = "se.scalablesolutions.akka" % "akka-actor"  % "1.0-RC5"
  val akkaTypedActor = "se.scalablesolutions.akka" % "akka-typed-actor"  % "1.0-RC5"
  lazy val redis = "net.debasishg" % "redisclient_2.8.1" % "2.3" % "compile"

  val scalatest = "org.scalatest" % "scalatest" % "1.2" % "test"

  val junit = "junit" % "junit" % "4.8.1"

  override def packageSrcJar = defaultJarPath("-sources.jar")
  lazy val sourceArtifact = Artifact.sources(artifactID)
  override def packageToPublishActions = super.packageToPublishActions ++ Seq(packageSrc)

  override def managedStyle = ManagedStyle.Maven
  Credentials(Path.userHome / ".ivy2" / ".credentials", log)
  lazy val publishTo = "Scala Tools Nexus" at "http://nexus.scala-tools.org/content/repositories/releases/"
//  lazy val publishTo = Resolver.file("Local Test Repository", Path fileProperty "java.io.tmpdir" asFile)
}
