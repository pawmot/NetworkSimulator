package com.pawmot.networkSimulator

abstract class NetworkPacket

case class Message(content: String) extends NetworkPacket
case class RIPSummary(table: RoutingTable) extends NetworkPacket
