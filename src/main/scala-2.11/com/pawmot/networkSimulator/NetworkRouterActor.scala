package com.pawmot.networkSimulator

import akka.actor.Actor

import scala.collection.mutable

class NetworkRouterActor(val Addr: Int) extends Actor with NetworkDevice {
  var routingTable = mutable.Map[Int, RoutingEntry]()

  override def receive: Receive = netDev orElse {
    case m@Message(_, addr) =>
      routingTable.get(addr).foreach(re => re.link ! m)
  }

  override def handle(content: String): Unit = ???
}
