package com.pawmot.networkSimulator.tests

import akka.actor.ActorSystem
import akka.testkit.{TestActorRef, TestProbe}
import com.pawmot.networkSimulator.{Message, ComputerActor}
import com.pawmot.networkSimulator.ComputerActor.SendMessage
import com.pawmot.networkSimulator.NetworkLinkActor.Connected
import com.pawmot.networkSimulator.tests.util.UnitSpec

class ComputerActorSpec extends UnitSpec[ComputerActor](ActorSystem("ComputerActorSpec")) {
  override def makeActor(): TestActorRef[ComputerActor] = TestActorRef(new ComputerActor(10))

  "ComputerActor" should {
    "send the correct message after receiving SendMessage" in {
      val a = makeActor()

      val p = TestProbe()

      a.tell(Connected, p.ref)
      a ! SendMessage("lol", 10)

      p.expectMsg(Message("lol", 10))
    }
  }
}
