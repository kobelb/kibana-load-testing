package org.kibanaLoadTest.simulation

import io.gatling.core.Predef._
import org.kibanaLoadTest.scenario.{Auth, Discover}

import scala.concurrent.duration.DurationInt

class DemoJourney extends BaseSimulation {
  val scenarioName = s"Kibana demo journey ${appConfig.buildVersion}"

  val scn = scenario(scenarioName)
    .exec(Discover.doQuery(appConfig.baseUrl, defaultHeaders))
      .exec(Auth.doQuery(appConfig.baseUrl, defaultHeaders))

  setUp(
    scn.inject(
      constantConcurrentUsers(20) during (1 minute), // 1
//      rampConcurrentUsers(20) to (50) during (3 minute) // 2
    ).protocols(httpProtocol)
  ).maxDuration(15 minutes)

  // generate a closed workload injection profile
  // with levels of 10, 15, 20, 25 and 30 concurrent users
  // each level lasting 10 seconds
  // separated by linear ramps lasting 10 seconds
}