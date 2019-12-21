package com.whitehatgaming.chess.models.piece

import com.whitehatgaming.chess.models.piece.rules.DirectionType

trait Piece {

  var isKilled = false
  var isMultiStep: Boolean = true

  var isBlack: Boolean
  var directionTypes: List[DirectionType]

  override def toString: String
}

object Piece {

  val BISHOP = "Bishop"
  val KING = "King"
  val KNIGHT = "KNight"
  val PAWN = "Pawn"
  val QUEEN = "Queen"
  val ROOK = "Rook"

}
