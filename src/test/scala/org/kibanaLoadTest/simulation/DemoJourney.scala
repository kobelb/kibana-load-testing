package org.kibanaLoadTest.simulation

import io.gatling.core.Predef._
import org.kibanaLoadTest.scenario.{Auth, Login}

import scala.concurrent.duration.DurationInt

class DemoJourney extends BaseSimulation {
  val scenarioName = s"Kibana demo journey ${appConfig.buildVersion}"

  val scn = scenario(scenarioName)
    .exec(Login.doLogin(appConfig.isSecurityEnabled, appConfig.loginPayload, appConfig.loginStatusCode))
      .exec(Auth.doQuery(appConfig.baseUrl, defaultHeaders))
    .exec(Auth.doQuery(appConfig.baseUrl, defaultHeaders))
    .exec(Auth.doQuery(appConfig.baseUrl, defaultHeaders))
    .exec(Auth.doQuery(appConfig.baseUrl, defaultHeaders))
    .exec(Auth.doQuery(appConfig.baseUrl, defaultHeaders))

  setUp(
    scn.inject(
      constantConcurrentUsers(20) during (1 minute), // 1
    ).protocols(httpProtocol)
  )
}