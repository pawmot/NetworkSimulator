package com.pawmot.networkSimulator.tests

import akka.actor.ActorSystem
import akka.testkit._
import com.pawmot.networkSimulator.NetworkLinkActor.{Connected, Break, ConnectTo, Fix}
import com.pawmot.networkSimulator.tests.util.UnitSpec
import com.pawmot.networkSimulator.{Message, NetworkLinkActor}

import scala.language.postfixOps

class NetworkLinkActorSpec extends UnitSpec[NetworkLinkActor](ActorSystem("NetworkLinkActorSpec")) {
  "NetworkLinkActor" should {
    "properly transmit message after being connected" in {
      val a = makeActor()

      val p1 = TestProbe()
      val p2 = TestProbe()
      p2.ignoreMsg({
        case Connected => true
      })

      a ! ConnectTo(p1.ref)
      a ! ConnectTo(p2.ref)

      a.tell(Message("SYN", 10), p1.ref)

      p2.expectMsg(Message("SYN", 10))
    }

    "not transmit message if it's broken" in {
      val a = makeActor()

      val p1 = TestProbe()
      val p2 = TestProbe()
      p2.ignoreMsg({
        case Connected => true
      })

      a ! ConnectTo(p1.ref)
      a ! ConnectTo(p2.ref)
      a ! Break

      a.tell(Message("SYN", 10), p1.ref)

      p2.expectNoMsg()
    }

    "transmit message if it's broken and then fixed" in {
      val a = makeActor()

      val p1 = TestProbe()
      val p2 = TestProbe()
      p2.ignoreMsg({
        case Connected => true
      })

      a ! ConnectTo(p1.ref)
      a ! ConnectTo(p2.ref)
      a ! Break
      a ! Fix

      a.tell(Message("SYN", 10), p1.ref)

      p2.expectMsg(Message("SYN", 10))
    }

    "send a Connected message to the component specified in the ConnectTo message" in {
      val a = makeActor()

      val p = TestProbe()

      a ! ConnectTo(p.ref)

      p.expectMsg(Connected)
    }
  }
}
