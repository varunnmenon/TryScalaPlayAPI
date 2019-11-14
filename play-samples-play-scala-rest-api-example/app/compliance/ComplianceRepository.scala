package compliance

import javax.inject.{Inject, Singleton}
import akka.actor.ActorSystem
import play.api.libs.concurrent.CustomExecutionContext
import play.api.{Logger, MarkerContext}
import play.api.libs.json._

import scala.concurrent.Future

final case class ComplianceData(type_Obj: TypeString, compliance: ComplianceObject)

final case class ComplianceObject(gdpr: Option[String], unprotected_devices: Option[String], uninspectable_data: Option[String])
object ComplianceObject {
  /**
    * Mapping to read/write a PostResource out as a JSON value.
    */
  implicit val format: Format[ComplianceObject] = Json.format
}

class TypeString private (val underlying: String) extends AnyVal {
  override def toString: String = underlying.toString
}

object TypeString {
  def apply(raw: String): TypeString = {
    require(raw != null)
    new TypeString(raw)
  }
}

class ComplianceExecutionContext @Inject()(actorSystem: ActorSystem)
  extends CustomExecutionContext(actorSystem, "repository.dispatcher")

/**
  * A pure non-blocking interface for the PostRepository.
  */
trait ComplianceRepository {
  def list()(implicit mc: MarkerContext): Future[Iterable[ComplianceData]]

  def get(type_obj: TypeString)(implicit mc: MarkerContext): Future[Option[ComplianceData]]
}

/**
  * A trivial implementation for the Post Repository.
  *
  * A custom execution context is used here to establish that blocking operations should be
  * executed in a different thread than Play's ExecutionContext, which is used for CPU bound tasks
  * such as rendering.
  */
@Singleton
class ComplianceRepositoryImpl @Inject()()(implicit ec: ComplianceExecutionContext)
  extends ComplianceRepository {

  private val logger = Logger(this.getClass)
  private val randomGen = scala.util.Random

  private val complianceList = List(
    ComplianceData(TypeString("gdpr"), ComplianceObject(Some(randomGen.nextFloat().toString),None,None)),
    ComplianceData(TypeString("unprotected-devices"), ComplianceObject(None, Some(randomGen.nextFloat().toString),None)),
    ComplianceData(TypeString("uninspectable-data"), ComplianceObject(None, None, Some(randomGen.nextFloat().toString))),
    ComplianceData(TypeString(""), ComplianceObject(Some(randomGen.nextFloat().toString),Some(randomGen.nextFloat().toString),Some(randomGen.nextFloat().toString)))
  )

  override def list()(
    implicit mc: MarkerContext): Future[Iterable[ComplianceData]] = {
    Future {
      logger.trace(s"list: ")
      complianceList
    }
  }

  override def get(type_Obj: TypeString)(
    implicit mc: MarkerContext): Future[Option[ComplianceData]] = {
    Future {
      logger.trace(s"get: id = $type_Obj")
      complianceList.find(post => post.type_Obj == type_Obj)
    }
  }
}
