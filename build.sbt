import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.github.aoiroaoino",
      scalaVersion := "2.12.4",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "more-abstract-repository",
    libraryDependencies += "org.typelevel" %% "cats-core" % "1.0.1",
    libraryDependencies += "org.typelevel" %% "cats-free" % "1.0.1",
//    libraryDependencies += "org.scalikejdbc" %% "scalikejdbc" % "3.2.0",
    libraryDependencies += scalaTest % Test,
    addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.4")
  )

