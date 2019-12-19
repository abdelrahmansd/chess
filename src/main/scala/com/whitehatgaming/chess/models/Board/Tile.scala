package com.whitehatgaming.chess.models.Board

import com.whitehatgaming.chess.models.piece.Piece

case class Tile (coordinates: Point, var piece: Option[Piece]){

  def reset: Unit = {
    this.piece = None
  }

  def isValid: Boolean = {
    (coordinates.x <= 7 && coordinates.x >=0) && (coordinates.y <= 7 && coordinates.y >=0)
  }
}

object Tile {

}
