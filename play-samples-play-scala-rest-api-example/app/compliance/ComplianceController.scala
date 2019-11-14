package compliance

import javax.inject.Inject
import play.api.Logger
import play.api.libs.json._
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

class ComplianceController  @Inject()(cc: ComplianceControllerComponents)(
  implicit ec: ExecutionContext)
  extends ComplianceBaseController(cc) {

  private val logger = Logger(getClass)

  def index: Action[AnyContent] = PostAction.async { implicit request =>        //Show output to check for type "" or any other parameter
    logger.trace("index: ")
    if(request.uri.endsWith("compliance/")){
      postResourceHandler.lookup("").map { posts =>
        Ok(Json.toJson(posts))
      }
    }
    else{
      Future.successful(BadRequest("Bad Request. Please send appropriate Parameters!"))
    }
  }

  def process: Action[AnyContent] = PostAction.async { implicit request =>   //Show output for any other request String
    logger.trace("process: ")
    Future.successful(BadRequest("Bad Request. Please send appropriate Request!"))
  }

  def show(id: String): Action[AnyContent] = PostAction.async {       //Show output for parameter {type}
    implicit request =>
      logger.trace(s"show: id = $id")
      if(checkKeyWords(id) && request.uri.split(id).length == 1) {
        postResourceHandler.lookup(id).map { post =>
          val result = Json.toJson(post)

          Ok(result)
        }
      }
      else{
        Future.successful(NotFound("Parameter Not Found. Please check the parameter and try again!"))
      }
  }

  private def checkKeyWords (string: String) : Boolean = { //To check adn filter
    string match {
      case "gdpr" | "unprotected-devices" | "uninspectable-data" => true
      case _ => false
    }
  }
}
