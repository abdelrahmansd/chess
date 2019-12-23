package com.ebuson.chess.models.Game

case class Player(name: String, isBlack: Boolean, var isInCheck: Boolean = false) {
  override def toString: String = s"${name}${if (isBlack) "(black)" else "(white)"}"
}

object Player {}