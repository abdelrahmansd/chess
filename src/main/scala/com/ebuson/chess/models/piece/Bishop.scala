package com.ebuson.chess.models.piece

import com.ebuson.chess.models.piece.rules.DirectionType

/*
* The bishop can move any number of squares but only diagonally
*/
case class Bishop(_isBlack: Boolean) extends Piece {

  override var directionTypes: List[DirectionType] = List(DirectionType.Diagonal)
  override var isBlack: Boolean = _isBlack

  override def toString: String = s"${Piece.BISHOP}${if (isBlack) "(black)" else "(white)"}"
}

object Bishop {

}
