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

    case RIPv1Request => sender() ! RIPv1Response(tableToShare)
  }

  override def handle(content: String): Unit = {}

  private def tableToShare = {
    val s = sender()

    routingTable.filter(kvp => kvp._2.link != s).map(kvp => (kvp._1, kvp._2.hops)).toMap
  }
}

object NetworkRouterActor {
  case object Enable
  case object RIPv1Request
  case class RIPv1Response(table: Map[Int, Byte])
}