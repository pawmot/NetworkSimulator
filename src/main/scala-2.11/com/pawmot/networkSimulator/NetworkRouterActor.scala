package com.pawmot.networkSimulator

import akka.actor.{ActorRef, Actor}
import com.pawmot.networkSimulator.NetworkLinkActor.Connected

import scala.collection.mutable

class NetworkRouterActor(val Addr: Int) extends Actor with NetworkDevice {
  import com.pawmot.networkSimulator.NetworkRouterActor._

  var enabled = false
  var links = List[ActorRef]()
  var routingTable = mutable.Map[Int, RoutingEntry]()

  override def receive: Receive = netDev orElse {
    case m@Message(_, addr) =>
      routingTable.get(addr).foreach(re => re.link ! m)

    case Connected =>
      links ::= sender()

    case Enable =>
      if(!enabled) {
        enabled = true
        for(l <- links) l ! RIPv1Request
      }
  }

  override def handle(content: String): Unit = {}
}

object NetworkRouterActor {
  case object Enable
  case object RIPv1Request
}