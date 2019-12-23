package com.ebuson.chess.models.Game

import com.ebuson.chess.models.Board.Point
import com.ebuson.chess.models.piece.Piece

case class Move(current: Point, next: Point, player: Player)

object Move {

  case class MoveResponse(killedPiece: Option[Piece])

}

