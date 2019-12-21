package com.whitehatgaming.chess.controller

import com.whitehatgaming.UserInputFile
import com.whitehatgaming.chess.models.Board.Point
import com.whitehatgaming.chess.models.Game.Move.MoveResponse
import com.whitehatgaming.chess.models.Game.{Move, Player}
import com.whitehatgaming.chess.models.piece.Piece

class Game (input: UserInputFile) {

  val player1 = Player("player1", false)
  val player2 = Player("player2", true)

  var nextTurn: Player = player1

  def start: Unit = {

    val board = new Board()
    board.init
    board.render
    var gameOver = false
    var nextMove = input.nextMove
    while (nextMove != null && !gameOver) {
      scala.io.StdIn.readLine("press enter for next move!")
      val p1 = Point(nextMove(1), nextMove(0))
      val p2 = Point(nextMove(3), nextMove(2))

      val moveRes: MoveResponse = board.move(Move(p1, p2, nextTurn))
      if (!moveRes.isValid) println("Invalid Move!")
      else board.render

      if (moveRes.isValid && board.isKingChecked(p2)) println(s"${nextTurn.name}'s King Is CHECKED!")
      if (moveRes.killedPiece.isDefined && moveRes.killedPiece.get.toString == Piece.KING) {
        gameOver = true
        println(s"${nextTurn.toString} WIN!")
      }
      if (moveRes.isValid) nextTurn = if (nextTurn == player1) player2 else player1
      nextMove = input.nextMove
    }
  }

}

object Game {}