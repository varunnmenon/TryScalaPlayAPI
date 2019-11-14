// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/varunmenon/Desktop/Altada/TryScalaPlayAPI/play-samples-play-scala-rest-api-example/conf/routes
// @DATE:Thu Nov 14 13:44:04 GMT 2019

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._

import play.api.mvc._

import _root_.controllers.Assets.Asset

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:1
  compliance_ComplianceRouter_0: compliance.ComplianceRouter,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:1
    compliance_ComplianceRouter_0: compliance.ComplianceRouter
  ) = this(errorHandler, compliance_ComplianceRouter_0, "/")

  def withPrefix(addPrefix: String): Routes = {
    val prefix = play.api.routing.Router.concatPrefix(addPrefix, this.prefix)
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, compliance_ComplianceRouter_0, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    prefixed_compliance_ComplianceRouter_0_0.router.documentation,
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:1
  private[this] val prefixed_compliance_ComplianceRouter_0_0 = Include(compliance_ComplianceRouter_0.withPrefix(this.prefix + (if (this.prefix.endsWith("/")) "" else "/") + "compliance"))


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:1
    case prefixed_compliance_ComplianceRouter_0_0(handler) => handler
  }
}
