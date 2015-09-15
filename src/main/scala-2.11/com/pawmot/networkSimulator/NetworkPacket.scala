package com.pawmot.networkSimulator

abstract class NetworkPacket

case class Message(content: String, recipientAddress: Int) extends NetworkPacket
case class RIPSummary(table: Map[Int, Int]) extends NetworkPacket
