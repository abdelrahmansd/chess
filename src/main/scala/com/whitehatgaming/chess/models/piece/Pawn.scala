package com.whitehatgaming.chess.models.piece
import com.whitehatgaming.chess.models.piece.rules.DirectionType

/*
* The pawn can move one square forward on subsequent moves (when not taking an opponent piece)
* The pawn can move one square forward diagonally if taking an opponent piece
*/
case class Pawn(_isBlack: Boolean) extends Piece{

  var isFirstMove: Boolean = true
  isMultiStep = false
  override var isBlack: Boolean = _isBlack
  override var directionTypes: List[DirectionType] = List(DirectionType.Vertical, DirectionType.Diagonal)

  override def toString: String = s"${Piece.PAWN}${if(isBlack) "(black)" else "(white)"}"
}

object Pawn {

}
