package org.kibanaLoadTest.scenario

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration.DurationInt

object Login {
  private  val loginHeaders = Map(
  "Content-Type" -> "application/json",
  "kbn-xsrf" -> "xsrf"
)
  def doLogin(
               isSecurityEnabled: Boolean,
               loginPayload: String,
               loginStatusCode: Int
             ) = doIf(isSecurityEnabled) {
    exec(http("login")
      .post("/internal/security/login")
      .headers(loginHeaders)
      .body(StringBody(loginPayload)).asJson
      .check(status.is(loginStatusCode)))
  }
    .exitHereIfFailed
}
