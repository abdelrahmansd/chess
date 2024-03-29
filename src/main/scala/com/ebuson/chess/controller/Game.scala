package com.ebuson.chess.controller

import com.ebuson.chess.models.Board.Point
import com.ebuson.chess.models.Game.{Move, Player}
import com.ebuson.chess.models.piece.Piece
import com.whitehatgaming.UserInputFile

class Game(input: UserInputFile) {

  val player1 = Player("player1", false)
  val player2 = Player("player2", true)

  var currentTurnPlayer: Player = player1

  def start: Unit = {
    val board = new Board()
    board.init
    board.render()

    var gameOver = false
    var killedPieces: List[Piece] = List.empty
    var nextMove = input.nextMove
    while (nextMove != null && !gameOver) {
      scala.io.StdIn.readLine(s"press enter for next move(${if (currentTurnPlayer.isBlack) "(black)" else "(white)"})!")
      val p1 = Point(nextMove(1), nextMove(0))
      val p2 = Point(nextMove(3), nextMove(2))
      val move: Move = Move(p1, p2, currentTurnPlayer)
      val isValidMove: Boolean = board.isValidMove(move)
      if (!isValidMove) println("\nInvalid Move!")
      else {
        val moveRes = board.move(move)
        if (board.isKingSafe(currentTurnPlayer.isBlack)) {
          if (moveRes.killedPiece.isDefined) killedPieces :+= moveRes.killedPiece.get
          currentTurnPlayer = if (currentTurnPlayer == player1) player2 else player1 // Flip player
          board.render(killedPieces)
          if (isValidMove && board.isKingChecked(p2)) println(s"\n${currentTurnPlayer.name}'s King Is CHECKED!")
          if (moveRes.killedPiece.isDefined && moveRes.killedPiece.get.toString.contains(Piece.KING)) {
            gameOver = true
            val winner = if (currentTurnPlayer == player1) player2 else player1
            println(s"\n${winner.toString} WIN!")
          }
        } else {
          println("You Are In CHECK!")
          board.undoMove(move, moveRes)
        }
      }
      nextMove = input.nextMove
    }
  }
}

object Game {}