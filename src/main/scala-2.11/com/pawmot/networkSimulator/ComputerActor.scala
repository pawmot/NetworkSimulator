package com.pawmot.networkSimulator

import akka.actor.{ActorRef, Actor}
import com.pawmot.networkSimulator.ComputerActor.SendMessage
import com.pawmot.networkSimulator.NetworkLinkActor.Connected

class ComputerActor(val Addr: Int) extends Actor with NetworkDevice {
  var link: Option[ActorRef] = None

  override def receive = netDev orElse {
    case Connected =>
      link = Some(sender())

    case SendMessage(content, addr) =>
      link.foreach(l => l ! Message(content, addr))
  }

  override def handle(content: String): Unit = ???
}

object ComputerActor {
  case class SendMessage(content: String, recipientAddress: Int)
}