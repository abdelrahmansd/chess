package com.whitehatgaming.chess.models.Game

import com.whitehatgaming.UserInputFile
import com.whitehatgaming.chess.models.Board.{Board, Point}

class Game (input: UserInputFile) {

  val player1 = Player("Ebu", false)
  val player2 = Player("Fady", true)

  var nextTurn: Player = player1

def start: Unit = {

    val board = new Board()
    board.init
    board.render

    var nextMove = input.nextMove
    while (nextMove != null) {
     scala.io.StdIn.readLine("press enter for next move!")
        val p1 = Point(nextMove(1), nextMove(0))
        val p2 = Point(nextMove(3), nextMove(2))
        println(board.getTile(p1))
        println(board.getTile(p2))

        val isValidMove = board.move(Move(p1, p2, None))
        if(!isValidMove) println("Invalid Move!")
        else {
          nextTurn = if(nextTurn == player1) player2 else player1
          board.render
        }
        if(isValidMove && board.isKingChecked(p2)) println( s"${nextTurn.name}'s King Is CHECKED!")
        nextMove = input.nextMove
    }


}

}

object Game {

}