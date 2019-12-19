package com.whitehatgaming.chess.models.piece

import com.whitehatgaming.chess.models.piece.rules.{DirectionType, MoveType}

/*
* The bishop can move any number of squares but only diagonally
*/
case class Bishop(_isBlack: Boolean) extends Piece {

  override var directionTypes: List[DirectionType] = List(DirectionType.Diagonal)
  override var isBlack: Boolean = _isBlack
  override def toString: String = Piece.BISHOP



}

object  Bishop {

}
