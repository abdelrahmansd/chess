package com.whitehatgaming.chess.controller

import com.whitehatgaming.UserInputFile
import com.whitehatgaming.chess.models.Board.Point
import com.whitehatgaming.chess.models.Game.Move.MoveResponse
import com.whitehatgaming.chess.models.Game.{Move, Player}
import com.whitehatgaming.chess.models.piece.Piece

class Game (input: UserInputFile) {

  val player1 = Player("player1", false)
  val player2 = Player("player2", true)


  var inCheck = scala.collection.mutable.Map((false -> false), (true -> false))
  var nextTurn: Player = player1

  def start: Unit = {

    val board = new Board()
    board.init
    board.render
    var gameOver = false
    var killedPieces: List[Piece] = List.empty
    var nextMove = input.nextMove
    while (nextMove != null && !gameOver) {
      scala.io.StdIn.readLine(s"press enter for next move(${if(nextTurn.isBlack) "(black)" else "(white)"})!")
      val p1 = Point(nextMove(1), nextMove(0))
      val p2 = Point(nextMove(3), nextMove(2))

      val moveRes: MoveResponse = board.move(Move(p1, p2, nextTurn))
      if (!moveRes.isValid) println("\nInvalid Move!")
      else {
        if(moveRes.killedPiece.isDefined) killedPieces :+= moveRes.killedPiece.get
        board.render
        if (inCheck(nextTurn.isBlack))  println(" Player In CHECK!")
        nextTurn = if (nextTurn == player1) player2 else player1 // Flip player
        killedPieces foreach(piece => print(s"${piece} "))
      }

      if (moveRes.isValid && board.isKingChecked(p2)) {
        println(s"\n${nextTurn.name}'s King Is CHECKED!")
        inCheck(nextTurn.isBlack) = true

      }
      if (moveRes.killedPiece.isDefined && moveRes.killedPiece.get.toString.contains(Piece.KING)) {
        gameOver = true
        val winner = if (nextTurn == player1) player2 else player1
        println(s"\n${winner.toString} WIN!")
      }
      nextMove = input.nextMove
    }
  }

}

object Game {}