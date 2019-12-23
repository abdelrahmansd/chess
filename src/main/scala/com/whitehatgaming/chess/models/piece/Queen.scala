package com.whitehatgaming.chess.models.piece

import com.whitehatgaming.chess.models.piece.rules.DirectionType

/*
* The queen can move any number of squares horizontally, vertically or diagonally.
*/

case class Queen(_isBlack: Boolean) extends Piece {

  override var isBlack: Boolean = _isBlack
  override var directionTypes: List[DirectionType] = List(DirectionType.Vertical, DirectionType.Horizontal, DirectionType.Diagonal)

  override def toString: String = s"${Piece.QUEEN}${if (isBlack) "(black)" else "(white)"}"
}

object Queen {

}
