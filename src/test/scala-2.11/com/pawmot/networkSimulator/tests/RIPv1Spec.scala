package com.pawmot.networkSimulator.tests

import akka.actor.ActorSystem
import akka.testkit.{TestProbe, TestActorRef, ImplicitSender}
import com.pawmot.networkSimulator.NetworkLinkActor.Connected
import com.pawmot.networkSimulator.NetworkRouterActor
import com.pawmot.networkSimulator.NetworkRouterActor.{RIPv1Response, RIPv1Request, Enable}
import com.pawmot.networkSimulator.tests.util.UnitSpec

class RIPv1Spec extends UnitSpec[NetworkRouterActor](ActorSystem("RIPv1Spec")) with ImplicitSender {
  override def makeActor(): TestActorRef[NetworkRouterActor] = TestActorRef(new NetworkRouterActor(10))

  "NetworkRouterActor" should {
    "send a RIPv1RequestMessage after being enabled" in {
      val a = makeActor()

      a ! Connected
      a ! Enable

      expectMsg(RIPv1Request)
    }

    "not send a RIPv1RequestMessage if it's already enabled" in {
      val a = makeActor()

      a ! Connected
      a ! Enable

      receiveN(1)

      a ! Enable

      expectNoMsg()
    }
  }

  "After receiving RIPv1RequestMessage NetworkRouterActor" should {
    "respond with a RIPv1ResponseMessage" in {
      val a = makeActor()

      a ! RIPv1Request

      expectMsg(RIPv1Response)
    }
  }
}
