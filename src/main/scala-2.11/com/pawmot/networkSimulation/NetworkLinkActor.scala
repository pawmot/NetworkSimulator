package com.pawmot.networkSimulation

import akka.actor.{ActorRef, Actor}

class NetworkLinkActor extends Actor {
  import com.pawmot.networkSimulation.NetworkLinkActor._
  import context._

  var connectedPeers = List[ActorRef]()

  override def receive: Receive = {
    case Break => become({
      case Fix => unbecome()
    }, discardOld = false)

    case Fix => ()

    case packet@(Message(_) | RIPSummary(_)) =>
      
  }
}

object NetworkLinkActor {
  case class ConnectTo(socket: ActorRef)
  case object Break
  case object Fix
}