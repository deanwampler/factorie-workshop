import sbt._
import Keys._
import sbtassembly.Plugin._
import AssemblyKeys._

object BuildSettings {

  val Name    = "FactorieWorkshop"
  val Version = "0.1"

  val buildSettings = Defaults.defaultSettings ++ Seq (
    name          := Name,
    organization  := "com.concurrentthought",
    version       := Version,
    scalaVersion  := "2.10.1",
    description   := "A workshop for the Factor Graph system called FACTORIE",
    scalacOptions := Seq("-deprecation", "-encoding", "UTF-8", "-unchecked", "-feature"), //, "-explaintypes")
    shellPrompt   := ShellPrompt.Prompt
  )
}

// Shell prompt which show the current project,
// git branch and build version
object ShellPrompt {
  object devnull extends ProcessLogger {
    def info (s: => String) {}
    def error (s: => String) { }
    def buffer[T] (f: => T): T = f
  }
  def currBranch = (
    ("git status -sb" lines_! devnull headOption)
      getOrElse "-" stripPrefix "## "
  )

  val Prompt = {
    (state: State) => {
      val currProject = Project.extract (state).currentProject.id
      "%s (%s) %s> ".format (
        currProject, currBranch, BuildSettings.Version
      )
    }
  }
}

object Resolvers {
  val umass     = "IESL repo" at "https://dev-iesl.cs.umass.edu/nexus/content/groups/public/"
  val typesafe  = "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
  val sonatype  = "Sonatype Release" at "https://oss.sonatype.org/content/repositories/releases"
  val mvnrepo   = "MVN Repo" at "http://mvnrepository.com/artifact"
}

object Dependency {
  val factorie = "cc.factorie" %% "factorie" % "1.0-deft-march-2014"
  val junit    = "junit"        % "junit"    % "4.10"
}

object Dependencies {
  import Dependency._

  val dependencies = Seq(factorie)
}


object FactoryWorkshopBuild extends Build {
  import Resolvers._
  import Dependencies._
  import BuildSettings._

  lazy val factoryWorkshopProject = Project(
    id = BuildSettings.Name,
    base = file("."),
    settings = buildSettings ++ Seq(libraryDependencies ++= Dependencies.dependencies)
  ).settings(
    resolvers ++= Seq(umass, typesafe, sonatype, mvnrepo)
  )
}

