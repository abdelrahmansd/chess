package com.whitehatgaming.chess.models.piece

import com.whitehatgaming.chess.models.piece.rules.{DirectionType, MoveType}

/*
* The king can move only 1 square but in any direction
*/
case class King(_isBlack: Boolean) extends Piece {

  moveType = MoveType.Single
  override var isBlack: Boolean = _isBlack
  override var directionTypes: List[DirectionType] = List(DirectionType.Vertical, DirectionType.Horizontal, DirectionType.Diagonal)
  override def toString: String = Piece.KING
}

object King {

}
