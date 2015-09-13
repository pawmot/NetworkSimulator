package com.pawmot.networkSimulator

import akka.actor.{ActorRef, Actor}

class NetworkLinkActor extends Actor {
  import com.pawmot.networkSimulator.NetworkLinkActor._
  import context._

  var connectedPeers = List[ActorRef]()

  override def receive: Receive = {
    case Break => become({
      case Fix => unbecome()
    }, discardOld = false)

    case Fix => ()

    case ConnectTo(socket) =>
      if(connectedPeers.size < 2)
        connectedPeers ::= socket

    case packet@(Message(_) | RIPSummary(_)) =>
      val s = sender()
      val other = connectedPeers.filter(socket => socket != s).head
      other ! packet
  }
}

object NetworkLinkActor {
  case class ConnectTo(socket: ActorRef)
  case object Break
  case object Fix
}