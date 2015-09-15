package com.pawmot.networkSimulator.tests

import akka.actor.ActorSystem
import akka.testkit.TestProbe
import com.pawmot.networkSimulator.{Message, RoutingEntry, NetworkRouterActor}
import com.pawmot.networkSimulator.tests.util.UnitSpec

class NetworkRouterActorSpec extends UnitSpec[NetworkRouterActor](ActorSystem("NetworkRouterActorSpec")) {
  "NetworkRouterActor" should {
    val a = makeActor()

    val p1 = TestProbe()
    val p2 = TestProbe()
    val p3 = TestProbe()

    a.underlyingActor.routingTable.put(1, RoutingEntry(p3.ref, 1))
    a.underlyingActor.routingTable.put(2, RoutingEntry(p2.ref, 1))
    a.underlyingActor.routingTable.put(3, RoutingEntry(p1.ref, 1))

    "route a message to proper host (A)" in {
      a.tell(Message("lol", 1), p1.ref)

      p1.expectNoMsg()
      p2.expectNoMsg()
      p3.expectMsg(Message("lol", 1))
    }

    "route a message to proper host (B)" in {
      a.tell(Message("lol", 2), p1.ref)

      p1.expectNoMsg()
      p2.expectMsg(Message("lol", 2))
      p3.expectNoMsg()
    }
  }
}
