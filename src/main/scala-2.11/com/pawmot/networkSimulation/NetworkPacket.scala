package com.pawmot.networkSimulation

abstract class NetworkPacket

case class Message(content: String) extends NetworkPacket
case class RIPSummary(table: RoutingTable) extends NetworkPacket
