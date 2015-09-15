package com.pawmot.networkSimulator

import akka.actor.Actor

trait NetworkDevice extends Actor {
  val Addr: Int

  def handle(content: String)

  def netDev: Receive = {
    case Message(content, Addr) => handle(content)
  }
}
