package com.ebuson.chess.models.piece.rules

trait DirectionType {
  val availableMoves: List[(Int, Int)]
}

object DirectionType {

  case object Horizontal extends DirectionType {
    override val availableMoves = List(
      (0, 1), // left
      (0, -1), // right
    )
  }

  case object Vertical extends DirectionType {
    override val availableMoves = List(
      (-1, 0), // up
      (1, 0), // down
    )
  }

  case object Diagonal extends DirectionType {
    override val availableMoves = List(
      (1, 1), // down right
      (1, -1), // down left
      (-1, 1), // up right
      (-1, -1), // up left
    )
  }

  case object LShape extends DirectionType {
    override val availableMoves = List(
      (2, 1), // down right
      (-2, 1), // down left
      (2, -1), // up right
      (-2, -1), // up left
      (1, -2), // right down
      (-1, -2), // left down
      (1, 2), // right up
      (-1, 2) // left up
    )
  }

}
