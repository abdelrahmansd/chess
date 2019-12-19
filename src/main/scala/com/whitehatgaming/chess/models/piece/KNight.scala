package com.whitehatgaming.chess.models.piece
import com.whitehatgaming.chess.models.piece.rules.DirectionType

/*
* The knight can move in an L shape with sides of 2 and 1 squares respectively.
* That is 8 different possible moves.   Unlike other pieces it jumps over other pieces.
*/
case class KNight(_isBlack: Boolean) extends Piece {

  override var directionTypes: List[DirectionType] = List(DirectionType.LShape)
  override var isBlack: Boolean = _isBlack
  override def toString: String = Piece.KNIGHT
}

object KNight {

}
