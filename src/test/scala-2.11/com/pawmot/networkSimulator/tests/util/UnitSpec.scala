package com.pawmot.networkSimulator.tests.util

import akka.actor.{Props, ActorRef, Actor, ActorSystem}
import akka.testkit._
import org.scalatest._

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.reflect.ClassTag

class UnitSpec[A <: Actor : ClassTag] (system: ActorSystem) extends TestKit(system) with WordSpecLike with MustMatchers with BeforeAndAfterAll with ParallelTestExecution {
  implicit def s = system

  override protected def afterAll(): Unit = {
    Await.result(system.terminate(), atMost = 10 seconds)
  }

  def makeActor() = TestActorRef[A]

}
