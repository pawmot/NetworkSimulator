package com.pawmot.networkSimulator.tests

import akka.actor.ActorSystem
import akka.testkit.{TestProbe, TestActorRef, ImplicitSender}
import com.pawmot.networkSimulator.NetworkLinkActor.Connected
import com.pawmot.networkSimulator.{RoutingEntry, NetworkRouterActor}
import com.pawmot.networkSimulator.NetworkRouterActor.{RIPv1Response, RIPv1Request, Enable}
import com.pawmot.networkSimulator.tests.util.UnitSpec

import scala.concurrent.duration._
import scala.language.postfixOps

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

      expectMsg(RIPv1Response(Map()))
    }
  }

  "RIPv1ResponseMessage" should {
    "[Split Horizon] not include the routes that begin with the target" in {
      val a = makeActor()

      val p1 = TestProbe()
      val p2 = TestProbe()

      a.underlyingActor.routingTable.put(1, RoutingEntry(p2.ref, 1))
      a.underlyingActor.routingTable.put(2, RoutingEntry(p1.ref, 1))

      a.tell(RIPv1Request, p1.ref)

      val msg1: RIPv1Response = p1.receiveOne(3 seconds).asInstanceOf[RIPv1Response]

      assertResult(1)(msg1.table.size)
      assert(msg1.table.contains(1))

      a.tell(RIPv1Request, p2.ref)

      val msg2 = p2.receiveOne(3 seconds).asInstanceOf[RIPv1Response]

      assertResult(1)(msg2.table.size)
      assert(msg2.table.contains(2))
    }
  }
}
