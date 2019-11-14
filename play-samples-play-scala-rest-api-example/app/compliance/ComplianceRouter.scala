package compliance

import javax.inject.Inject
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class ComplianceRouter  @Inject()(controller: ComplianceController) extends SimpleRouter {
  /**
    * Routes and URLs to the Compliance controller.
    */
    override def routes: Routes = {
      case GET(p"/") =>
        controller.index

      case GET(p"/$typeStr") =>
        controller.show(typeStr)  //Routes to show function which takes Type as parameter in the ComplianceController

      case _ =>
        controller.process  //Routes to process function in the ComplianceController for a default case
    }
}
