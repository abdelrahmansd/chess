package com.whitehatgaming.chess.models.piece
import com.whitehatgaming.chess.models.rules.DirectionType

case class Pawn(isBlack: Boolean) extends Piece{

  override def isBiDirectional: Boolean = false

  override var direction: DirectionType = List(DirectionType.Vertical

}

object Pawn {
  def isFirst: Boolean = false

}
