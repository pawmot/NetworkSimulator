package tests

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.testkit.{TestProbe, TestKit}
import com.pawmot.networkSimulation.NetworkLinkActor.{Fix, Break, ConnectTo}
import com.pawmot.networkSimulation.{Message, NetworkLinkActor}
import org.scalatest._

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

class NetworkLinkActorSpec extends TestKit(ActorSystem("NetworkLinkActorSpec")) with WordSpecLike with MustMatchers with BeforeAndAfterAll with ParallelTestExecution {
  override protected def afterAll(): Unit = {
    Await.result(system.terminate(), atMost = 10 seconds)
  }

  def makeActor(): ActorRef = system.actorOf(Props[NetworkLinkActor], "NetworkLinkActor")

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
