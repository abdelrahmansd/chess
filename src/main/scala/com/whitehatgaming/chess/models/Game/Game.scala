package com.whitehatgaming.chess.models.Game

import com.whitehatgaming.UserInputFile
import com.whitehatgaming.chess.models.Board.{Board}

case class Game (player1: Player,
            player2: Player,
            board: Board,
            nextTurn: Player,
            moves: List[Move],
            result: Option[String]) {


    val input = new UserInputFile("")

}

object Game {

}