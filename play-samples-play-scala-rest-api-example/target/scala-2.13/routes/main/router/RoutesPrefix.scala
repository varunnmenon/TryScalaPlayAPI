// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/varunmenon/Desktop/Altada/TryScalaPlayAPI/play-samples-play-scala-rest-api-example/conf/routes
// @DATE:Thu Nov 14 13:44:04 GMT 2019


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
