package com.pawmot.networkSimulator

import akka.actor.ActorRef

case class RoutingEntry(link: ActorRef, hops: Byte)
