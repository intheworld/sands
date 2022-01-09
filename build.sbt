name := "sands"

version := "0.1"

scalaVersion := "2.12.7"

addCompilerPlugin("edu.berkeley.cs" % "chisel3-plugin" % "3.5.0-RC2" cross CrossVersion.full)
libraryDependencies += "edu.berkeley.cs" %% "chisel3" % "3.5.0-RC1"
libraryDependencies += "edu.berkeley.cs" %% "chiseltest" % "0.5.0-RC2"
// https://mvnrepository.com/artifact/edu.berkeley.cs/rocketchip
libraryDependencies += "edu.berkeley.cs" %% "rocketchip" % "1.2.6"
// https://mvnrepository.com/artifact/edu.berkeley.cs/rocket-dsptools
libraryDependencies += "edu.berkeley.cs" %% "rocket-dsptools" % "1.2.6"