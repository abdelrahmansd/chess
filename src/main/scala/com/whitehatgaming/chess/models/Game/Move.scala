package com.whitehatgaming.chess.models.Game

import com.whitehatgaming.chess.models.Board.{Point, Tile}
import com.whitehatgaming.chess.models.piece.Piece

case class Move (current: Point, next: Point, player: Player)

object Move {
  case class MoveResponse(isValid: Boolean, killedPiece: Option[Piece])
}

