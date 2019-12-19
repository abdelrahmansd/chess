package com.whitehatgaming.chess.models.piece.rules

trait DirectionType

object DirectionType {

  case object Horizontal extends DirectionType

  case object Vertical extends DirectionType

  case object Diagonal extends DirectionType

  case object LShape extends DirectionType

}
