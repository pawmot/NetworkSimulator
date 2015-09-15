package com.pawmot.networkSimulator

import akka.actor.Actor

import scala.collection.mutable

class NetworkRouterActor extends Actor {
  var routingTable = mutable.Map[Int, RoutingEntry]()

  override def receive: Receive = {
    case m@Message(_, addr) =>
      routingTable.get(addr).foreach(re => re.link ! m)
  }
}
