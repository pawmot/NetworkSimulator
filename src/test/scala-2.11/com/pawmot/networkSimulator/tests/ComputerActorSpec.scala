package com.pawmot.networkSimulator.tests

import akka.actor.ActorSystem
import akka.testkit.TestProbe
import com.pawmot.networkSimulator.ComputerActor
import com.pawmot.networkSimulator.ComputerActor.SendMessage
import com.pawmot.networkSimulator.tests.util.UnitSpec

class ComputerActorSpec extends UnitSpec[ComputerActor](ActorSystem("ComputerActorSpec")) {
  "ComputerActor" should {
    "send the correct message after receiving SendMessage" in {
      val a = makeActor()

      val p = TestProbe()

      a ! SendMessage("lol", 10)
    }
  }
}
