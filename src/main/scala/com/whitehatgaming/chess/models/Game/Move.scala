package com.whitehatgaming.chess.models.Game

import com.whitehatgaming.chess.models.Board.{Point, Tile}
import com.whitehatgaming.chess.models.piece.Piece

//case class Move (current: Point, next: Point, player: Player, killedTile: Option[Tile])
case class Move (current: Point, next: Point, player: Player, var killedPiece: Option[Piece])

object Move {

}
