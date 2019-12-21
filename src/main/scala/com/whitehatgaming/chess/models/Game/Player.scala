package com.whitehatgaming.chess.models.Game

case class Player (name: String, isBlack: Boolean){
  override def toString: String = s"${name}${if(isBlack) "(black)" else "(white)"}"
}

object Player {}