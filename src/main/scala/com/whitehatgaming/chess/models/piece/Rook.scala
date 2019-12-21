package com.whitehatgaming.chess.models.piece
import com.whitehatgaming.chess.models.piece.rules.DirectionType

/*
* The rook can move any number of squares but only horizontally or vertically
*/

case class Rook(_isBlack: Boolean) extends Piece {

  override var isBlack: Boolean = _isBlack
  override var directionTypes: List[DirectionType] = List(DirectionType.Vertical, DirectionType.Horizontal)

  override def toString: String = s"${Piece.ROOK}${if(isBlack) "(black)" else "(white)"}"

}

object Rook {

}
