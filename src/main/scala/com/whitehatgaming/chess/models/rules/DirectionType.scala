package com.whitehatgaming.chess.models.rules

trait DirectionType

object DirectionType {

  case object Horizontal extends DirectionType

  case object Vertical extends DirectionType

  case object Diagonal extends DirectionType

}
