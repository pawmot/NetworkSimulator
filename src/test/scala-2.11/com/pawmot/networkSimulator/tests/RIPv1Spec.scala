package com.pawmot.networkSimulator.tests

import akka.actor.ActorSystem
import akka.testkit.{TestProbe, TestActorRef}
import com.pawmot.networkSimulator.NetworkLinkActor.Connected
import com.pawmot.networkSimulator.NetworkRouterActor
import com.pawmot.networkSimulator.NetworkRouterActor.{RIPv1Request, Enable}
import com.pawmot.networkSimulator.tests.util.UnitSpec

class RIPv1Spec extends UnitSpec[NetworkRouterActor](ActorSystem("RIPv1Spec")) {
  override def makeActor(): TestActorRef[NetworkRouterActor] = TestActorRef(new NetworkRouterActor(10))

  "NetworkRouterActor" should {
    "send a RIPv1RequestMessage after being enabled" in {
      val a = makeActor()

      val p = TestProbe()

      a.tell(Connected, p.ref)

      a ! Enable

      p.expectMsg(RIPv1Request)
    }

    "not send a RIPv1RequestMessage if it's already enabled" in {
      val a = makeActor()

      val p = TestProbe()

      a.tell(Connected, p.ref)

      a ! Enable

      p.receiveN(1)

      a ! Enable

      p.expectNoMsg()
    }
  }
}
