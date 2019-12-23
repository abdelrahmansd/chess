package com.whitehatgaming.chess.models.Game

import com.whitehatgaming.chess.models.Board.Point
import com.whitehatgaming.chess.models.piece.Piece

case class Move(current: Point, next: Point, player: Player)

object Move {

  case class MoveResponse(killedPiece: Option[Piece])

}

