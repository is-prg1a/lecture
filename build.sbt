// 重要な注意：各設定の間に必ず空行を挿入して下さい。
// これを守らないと sbt が起動しません。

name         := "lecture" // プロジェクトの名称

version      := "1.0"     // プロジェクトのバージョン番号

scalaVersion := "2.11.8"  // コンパイルに使う scalac のバージョン

scalacOptions ++=         // scalac に与えるオプション
  Seq("-optimize", "-feature", "-unchecked", "-deprecation")

javaOptions in run ++=    // 仮想機械に与えるオプション
  Seq( "-Xmx2G", "-verbose:gc")                          

// プロジェクトで使う非標準 Scala ライブラリ

// The dependency on Scalactic, ScalaTest's sister library focused on
// quality through types, is recommended but not required.
//     (http://www.scalatest.org/install)

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.0"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test"

// データ生成テストのために使う ScalaCheck ライブラリ
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.13.2" % "test"

// JSONデータを扱うためのライブラリ
libraryDependencies +=
  "net.liftweb" % "lift-json_2.11" % "3.0-M8"

// 並行処理のためのライブラリ
libraryDependencies +=
  "com.typesafe.akka" % "akka-actor_2.11" % "2.4.10"

// テスト実行や通常の実行にsbtとは別のプロセスを用いる

fork in (Test, run) := true

connectInput := true

// ソースコードの在処を非標準の場所に設定

scalaSource in Compile := baseDirectory.value / "src"

scalaSource in Test := baseDirectory.value / "test"

// コンパイル結果を非標準の場所に設定
// この設定はコンパイルの副産物がDropbox等のクラウドストレージに保存されることを
// 避けるためのものです。これによりクラウドストレージとの同期時間が短縮されます。

target := Path.userHome / "tmp" / "sbt" / "cs1g" / name.value
