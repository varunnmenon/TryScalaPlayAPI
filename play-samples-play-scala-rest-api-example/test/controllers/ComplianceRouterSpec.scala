package controllers

import compliance.{ComplianceObject, ComplianceResource}
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.libs.json.{ JsResult, Json }
import play.api.mvc.{ RequestHeader, Result }
import play.api.test._
import play.api.test.Helpers._
import play.api.test.CSRFTokenHelper._

import scala.concurrent.Future

class ComplianceRouterSpec extends PlaySpec with GuiceOneAppPerTest {

  "ComplianceRouter" should {

    "render the compliance with no parameter" in {
      val request = FakeRequest(GET, "/compliance/").withHeaders(HOST -> "localhost:9000").withCSRFToken
      val home:Future[Result] = route(app, request).get
      val compliance: ComplianceResource = Json.fromJson[ComplianceResource](contentAsJson(home)).get
      status(home) mustBe (200)
      val x = compliance.compliance.unprotected_devices.isDefined
      val y = compliance.compliance.gdpr.isDefined
      val z = compliance.compliance.uninspectable_data.isDefined
      x mustBe true
      y mustBe true
      z mustBe true

      val gdprData = compliance.compliance.gdpr.getOrElse("1.000000000001")
      val unprotectedData = compliance.compliance.unprotected_devices.getOrElse("1.000000000001")
      val uninspectedData = compliance.compliance.uninspectable_data.getOrElse("1.000000000001")
      val data : Boolean = (0 <= gdprData.toFloat) && (gdprData.toFloat <= 1) && (0 <= unprotectedData.toFloat) && (unprotectedData.toFloat <= 1) && (0 <= uninspectedData.toFloat) && (uninspectedData.toFloat <= 1)
      data mustBe true
    }

    "render the comliances when url ends with a gdpr" in {
      val request = FakeRequest(GET, "/compliance/gdpr").withHeaders(HOST -> "localhost:9000").withCSRFToken
      val home:Future[Result] = route(app, request).get
      status(home) mustBe (200)
      val compliance: ComplianceResource = Json.fromJson[ComplianceResource](contentAsJson(home)).get
      val x = compliance.compliance.unprotected_devices.isDefined
      val y = compliance.compliance.gdpr.isDefined
      val z = compliance.compliance.uninspectable_data.isDefined
      x mustBe false
      y mustBe true
      z mustBe false

      val gdprData = compliance.compliance.gdpr.getOrElse("1.000000000001")
      val data : Boolean = (0 <= gdprData.toFloat) && (gdprData.toFloat <= 1)
      data mustBe true
    }

    "render the comliances when url ends with a unprotected-devices" in {
      val request = FakeRequest(GET, "/compliance/unprotected-devices").withHeaders(HOST -> "localhost:9000").withCSRFToken
      val home:Future[Result] = route(app, request).get
      status(home) mustBe (200)
      val compliance: ComplianceResource = Json.fromJson[ComplianceResource](contentAsJson(home)).get
      val x = compliance.compliance.unprotected_devices.isDefined
      val y = compliance.compliance.gdpr.isDefined
      val z = compliance.compliance.uninspectable_data.isDefined
      x mustBe true
      y mustBe false
      z mustBe false

      val unprotected_devices_Data = compliance.compliance.unprotected_devices.getOrElse("1.000000000001")
      val data : Boolean = (0 <= unprotected_devices_Data.toFloat) && (unprotected_devices_Data.toFloat <= 1)
      data mustBe true
    }

    "render the comliances when url ends with a uninspectable-data" in {
      val request = FakeRequest(GET, "/compliance/uninspectable-data").withHeaders(HOST -> "localhost:9000").withCSRFToken
      val home:Future[Result] = route(app, request).get
      status(home) mustBe (200)
      val compliance: ComplianceResource = Json.fromJson[ComplianceResource](contentAsJson(home)).get
      val x = compliance.compliance.unprotected_devices.isDefined
      val y = compliance.compliance.gdpr.isDefined
      val z = compliance.compliance.uninspectable_data.isDefined
      x mustBe false
      y mustBe false
      z mustBe true

      val uninspectable_Data = compliance.compliance.uninspectable_data.getOrElse("1.000000000001")
      val data : Boolean = (0 <= uninspectable_Data.toFloat) && (uninspectable_Data.toFloat <= 1)
      data mustBe true
    }

    "render the comliances when url ends with a gdpr, uninspectable-data, unprotected-devices and a /" in {
      val request = FakeRequest(GET, "/compliance/gdpr/").withHeaders(HOST -> "localhost:9000").withCSRFToken
      val home:Future[Result] = route(app, request).get
      status(home) mustBe NOT_FOUND

      val request1 = FakeRequest(GET, "/compliance/uninspectable-data/").withHeaders(HOST -> "localhost:9000").withCSRFToken
      val home1:Future[Result] = route(app, request1).get
      status(home1) mustBe NOT_FOUND

      val request2 = FakeRequest(GET, "/compliance/unprotected-devices/").withHeaders(HOST -> "localhost:9000").withCSRFToken
      val home2:Future[Result] = route(app, request2).get
      status(home2) mustBe NOT_FOUND
    }

    "render the comliances when url ends with a incorrect parameter" in {
      val request = FakeRequest(GET, "/compliance/smpr").withHeaders(HOST -> "localhost:9000").withCSRFToken
      val home:Future[Result] = route(app, request).get
      status(home) mustBe NOT_FOUND
    }

    "render the comliances when url ends with a extra parameter after ACTUAL parameter" in {
      val request = FakeRequest(GET, "/compliance/gdpr/mbpr").withHeaders(HOST -> "localhost:9000").withCSRFToken
      val home:Future[Result] = route(app, request).get
      status(home) mustBe BAD_REQUEST

      val request1 = FakeRequest(GET, "/compliance/uninspectable-data/asdfe").withHeaders(HOST -> "localhost:9000").withCSRFToken
      val home1:Future[Result] = route(app, request1).get
      status(home1) mustBe BAD_REQUEST

      val request2 = FakeRequest(GET, "/compliance/unprotected-devices/wasd").withHeaders(HOST -> "localhost:9000").withCSRFToken
      val home2:Future[Result] = route(app, request2).get
      status(home2) mustBe BAD_REQUEST
    }

    "render the comliances when url ends with gdpr but with Get, POST, PUT and DELETE calls " in {
      val requestG = FakeRequest(GET, "/compliance/gdpr").withHeaders(HOST -> "localhost:9000").withCSRFToken
      val homeGet:Future[Result] = route(app, requestG).get
      status(homeGet) mustBe OK

      val request = FakeRequest(POST, "/compliance/gdpr").withHeaders(HOST -> "localhost:9000").withCSRFToken
      val homePost:Future[Result] = route(app, request).get
      status(homePost) mustBe BAD_REQUEST

      val request1 = FakeRequest(PUT, "/compliance/gdpr").withHeaders(HOST -> "localhost:9000").withCSRFToken
      val homePut:Future[Result] = route(app, request1).get
      status(homePut) mustBe BAD_REQUEST

      val request2 = FakeRequest(DELETE, "/compliance/gdpr").withHeaders(HOST -> "localhost:9000").withCSRFToken
      val homeDelete:Future[Result] = route(app, request2).get
      status(homeDelete) mustBe BAD_REQUEST

    }
  }

}