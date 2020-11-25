package org.kibanaLoadTest.scenario

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Auth {

  def doQuery(baseUrl: String, headers: Map[String, String]) = exec(http("me")
    .get("/internal/security/me")
    .headers(headers)
    .check(status.is(200))
  )
}