package compliance

import javax.inject.{Inject, Provider}

import play.api.MarkerContext

import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.json._

case class ComplianceResource(compliance: ComplianceObject)

object ComplianceResource {
  /**
    * Mapping to read/write a PostResource out as a JSON value.
    */
  implicit val format: Format[ComplianceResource] = Json.format
}

class ComplianceResourceHandler @Inject()(
                                           routerProvider: Provider[ComplianceRouter],
                                           complianceRepository: ComplianceRepository)(implicit ec: ExecutionContext) {

  def lookup(id: String)(
    implicit mc: MarkerContext): Future[Option[ComplianceResource]] = {
    val complianceFuture = complianceRepository.get(TypeString(id))
    complianceFuture.map { maybeComplianceData =>
      maybeComplianceData.map { complianceData =>
        createComplianceResource(complianceData)
      }
    }
  }

  def find(implicit mc: MarkerContext): Future[Iterable[ComplianceResource]] = {
    complianceRepository.list().map { complianceDataList =>
      complianceDataList.map(complianceData => createComplianceResource(complianceData))
    }
  }

  private def createComplianceResource(p: ComplianceData): ComplianceResource = {
    ComplianceResource(p.compliance)
  }

}
