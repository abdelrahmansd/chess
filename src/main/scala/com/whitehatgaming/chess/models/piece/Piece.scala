package com.whitehatgaming.chess.models.piece

import com.whitehatgaming.chess.models.rules.DirectionType

trait Piece {
  var killed = false
  var direction: List[DirectionType]

  override def toString: String
  def isKilled: Boolean = killed
  def isBiDirectional: Boolean = true

  def kill(): Unit  = {
    killed = true
  }
}

object Piece {

}
