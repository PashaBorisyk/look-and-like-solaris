import com.typesafe.sbt.packager.docker.{DockerChmodType}
import sbt.Attributed

enablePlugins(DockerPlugin)

name := "look-and-like-solaris"

version := "1.4"

lazy val `lookandlikesolaris` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(jdbc, ehcache, ws, specs2 % Test, guice)
libraryDependencies += "io.swagger" %% "swagger-play2" % "1.7.1"

mainClass in assembly := Some("play.core.server.ProdServerStart")
fullClasspath in assembly += Attributed.blank(PlayKeys.playPackageAssets.value)

assemblyMergeStrategy in assembly := {
   case manifest if manifest.contains("MANIFEST.MF") =>
      // We don't need manifest files since sbt-assembly will create
      // one with the given settings
      MergeStrategy.discard
   case referenceOverrides if referenceOverrides.contains("reference-overrides.conf") =>
      // Keep the content for all reference-overrides.conf files
      MergeStrategy.concat
   case x =>
      // For all the other files, use the default sbt-assembly merge strategy
      val oldStrategy = (assemblyMergeStrategy in assembly).value
      oldStrategy(x)
}


javaOptions in Universal ++= Seq(
   "-Dpidfile.path=/dev/null"
)

dockerUsername := Some("lookandlike")
dockerChmodType := DockerChmodType.UserGroupWriteExecute
dockerAdditionalPermissions += (DockerChmodType.UserGroupWriteExecute, "/opt/docker/")
dockerExposedPorts in Docker := Seq(9000)
