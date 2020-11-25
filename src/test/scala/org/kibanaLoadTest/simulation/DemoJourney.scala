package org.kibanaLoadTest.simulation

import io.gatling.core.Predef._
import org.kibanaLoadTest.scenario.{Canvas, Dashboard, Discover, Login}

import scala.concurrent.duration.DurationInt

class DemoJourney extends BaseSimulation {
  val scenarioName = s"Kibana demo journey ${appConfig.buildVersion}"

  val scn = scenario(scenarioName)
    .exec(Login.doLogin(appConfig.isSecurityEnabled, appConfig.loginPayload, appConfig.loginStatusCode).pause(5 seconds))
    .exec(Discover.doQuery(appConfig.baseUrl, defaultHeaders).pause(10 seconds))
    .exec(Dashboard.load(appConfig.baseUrl, defaultHeaders).pause(10 seconds))
    .exec(Canvas.loadWorkpad(appConfig.baseUrl, defaultHeaders))

  setUp(
    scn.inject(
      constantConcurrentUsers(20) during (1 minute), // 1
    ).protocols(httpProtocol)
  )

  // generate a closed workload injection profile
  // with levels of 10, 15, 20, 25 and 30 concurrent users
  // each level lasting 10 seconds
  // separated by linear ramps lasting 10 seconds
}