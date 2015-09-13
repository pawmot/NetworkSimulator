package com.pawmot.networkSimulator.tests

import akka.actor.ActorSystem
import akka.testkit._
import com.pawmot.networkSimulator.NetworkLinkActor.{Break, ConnectTo, Fix}
import com.pawmot.networkSimulator.tests.util.UnitSpec
import com.pawmot.networkSimulator.{Message, NetworkLinkActor}

import scala.language.postfixOps

class NetworkLinkActorSpec extends UnitSpec[NetworkLinkActor](ActorSystem("NetworkLinkActorSpec")) {

  "NetworkLinkActor" should {
    "transmit message after being connected" in {
      val a = makeActor()

      val p1 = TestProbe()
      val p2 = TestProbe()

      a ! ConnectTo(p1.ref)
      a ! ConnectTo(p2.ref)

      a.tell(Message("SYN"), p1.ref)

      p2.expectMsg(Message("SYN"))
    }

    "not transmit message if it's broken" in {
      val a = makeActor()

      val p1 = TestProbe()
      val p2 = TestProbe()

      a ! ConnectTo(p1.ref)
      a ! ConnectTo(p2.ref)
      a ! Break

      a.tell(Message("SYN"), p1.ref)

      p2.expectNoMsg()
    }

    "transmit message if it's broken and then fixed" in {
      val a = makeActor()

      val p1 = TestProbe()
      val p2 = TestProbe()

      a ! ConnectTo(p1.ref)
      a ! ConnectTo(p2.ref)
      a ! Break
      a ! Fix

      a.tell(Message("SYN"), p1.ref)

      p2.expectMsg(Message("SYN"))
    }
  }
}
