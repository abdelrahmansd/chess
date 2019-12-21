package com.whitehatgaming.chess.models.Game

import com.whitehatgaming.UserInputFile
import com.whitehatgaming.chess.models.Board.{Board, Point}

class Game (input: UserInputFile) {

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
        else board.render
        if(isValidMove && board.isKingChecked(p2)) println( "player X King CHECKED!")
        nextMove = input.nextMove
    }


}

}

object Game {

}