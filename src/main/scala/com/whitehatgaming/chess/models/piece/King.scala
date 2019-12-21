package com.whitehatgaming.chess.models.piece

import com.whitehatgaming.chess.models.piece.rules.DirectionType

/*
* The king can move only 1 square but in any direction
*/
case class King(_isBlack: Boolean) extends Piece {

  override var isBlack: Boolean = _isBlack
  override var directionTypes: List[DirectionType] = List(DirectionType.Vertical, DirectionType.Horizontal, DirectionType.Diagonal)
  isMultiStep = false
  override def toString: String = s"${Piece.KING}${if(isBlack) "(black)" else "(white)"}"
}

object King {

}
