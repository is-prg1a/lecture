lazy val commonSettings = Seq(
  version := "1.0",           // プロジェクトのバージョン番号
  scalaVersion := "2.11.8",   // コンパイルに使う scalac のバージョン
  scalacOptions := Seq(       // scalac に与えるオプション
    "-optimize",
    "-feature",
    "-unchecked",
    "-deprecation"
  ),
  javaOptions in run := Seq(  // 仮想機械に与えるオプション
    "-Xmx2G",
    "-verbose:gc"
  )
)

lazy val scalafxSettings = {
  val javaVersion = sys.props("java.specification.version")

  assert(javaVersion == "1.8" || javaVersion == "1.7",
    f"ScalaFX supports Java versions 1.7 and higher.  Current version is ($javaVersion).  Please upgrade the version of your Java Development Kit")

  val scalaFxDependency = javaVersion match {
    case "1.8" =>
      Seq(libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.92-R10")
    case "1.7" =>
      Seq(libraryDependencies += "org.scalafx" %% "scalafx" % "2.2.76-R11",
        unmanagedJars in Compile += Attributed.blank(file(scala.util.Properties.javaHome) / "/lib/jfxrt.jar"))
  }

  Defaults.coreDefaultSettings ++ scalaFxDependency
}

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(scalafxSettings: _*).
  settings(
    name := "lecture",                                      // プロジェクトの名称
    libraryDependencies ++= Seq(                            // プロジェクトで使う非標準 Scala ライブラリ
      "org.scalactic" %% "scalactic" % "3.0.0",
      "org.scalatest" %% "scalatest" % "3.0.0" % "test",
      "org.scalacheck" %% "scalacheck" % "1.13.2" % "test", // データ生成テストのために使う ScalaCheck ライブラリ
      "net.liftweb" %% "lift-json" % "3.0-M8",              // JSONデータを扱うためのライブラリ
      "com.typesafe.akka" %% "akka-actor" % "2.4.10"        // 並行処理のためのライブラリ
    ),
    fork in (Test, run) := true,
    connectInput := true,
    scalaSource in Compile := baseDirectory.value / "src",  // ソースコードの在処を非標準の場所に設定
    scalaSource in Test := baseDirectory.value / "test",
    // コンパイル結果を非標準の場所に設定
    // この設定はコンパイルの副産物がDropbox等のクラウドストレージに保存されることを
    // 避けるためのものです。これによりクラウドストレージとの同期時間が短縮されます。
    target := Path.userHome / "tmp" / "sbt" / "cs1g" / name.value
  )

