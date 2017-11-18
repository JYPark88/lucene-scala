name := "lucene-scala"

version := "0.1"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "org.apache.lucene" % "lucene-core" % "7.1.0",
  "org.apache.lucene" % "lucene-queryparser" % "7.1.0"
)