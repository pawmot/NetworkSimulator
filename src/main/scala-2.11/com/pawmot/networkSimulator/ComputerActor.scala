package com.pawmot.networkSimulator

import akka.actor.{ActorRef, Actor}
import com.pawmot.networkSimulator.ComputerActor.SendMessage
import com.pawmot.networkSimulator.NetworkLinkActor.Connected

class ComputerActor extends Actor {
  var link: Option[ActorRef] = None

  override def receive: Receive = {
    case Connected =>
      link = Some(sender())

    case SendMessage(content, addr) =>
      link.foreach(l => l ! Message(content, addr))
  }
}

object ComputerActor {
  case class SendMessage(content: String, recipientAddress: Int)
}