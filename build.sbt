lazy val buildSettings = Seq (
  name             := "FactorieWorkshop",
  organization     := "com.concurrentthought",
  version          := "0.1.1",
  scalaVersion     := "2.11.12",  // most recent version of Scala supported by Factorie
  description      := "A workshop for the Factor Graph system called FACTORIE",
  scalacOptions    := Seq("-deprecation", "-encoding", "UTF-8", "-unchecked", "-feature"), //, "-explaintypes")
  test in assembly := {}
)

lazy val umass = "IESL repo" at "https://dev-iesl.cs.umass.edu/nexus/content/groups/public/"

lazy val factorie = "cc.factorie" %% "factorie" % "1.2"
lazy val junit    = "junit"        % "junit"    % "4.10" % "test"

lazy val factoryWorkshopProject = project.in(file("."))
  .settings(buildSettings)
  .settings(libraryDependencies := Seq(factorie, junit))
  .settings(resolvers ++= Seq(umass))

