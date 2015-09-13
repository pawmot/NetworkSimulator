package com.pawmot.networkSimulator

import akka.actor.Actor

class ComputerActor extends Actor {
  override def receive: Receive = ???
}

object ComputerActor {
  case class SendMessage(content: String, recipientAddress: Int)
}