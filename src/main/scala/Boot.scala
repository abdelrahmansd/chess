import com.whitehatgaming.chess.models.Board.{Board, Point}
import com.whitehatgaming.UserInputFile
import com.whitehatgaming.chess.models.Game.Move

object Boot extends App {

  println("Hello chess!")

  val input = new UserInputFile("/run/media/thug/OLD-REPOS/swvl/chess-demo/src/test/scala/data/sample-moves.txt")
  input.nextMove()
  var move = input.nextMove()
  val board = new Board()
  board.init
  board.render

  println(move(0))
  println(move(1))
  println(move(2))
  println(move(3))
  println(board.getTile(Point(move(0),move(1))))
  println(board.avaliableMoves(Point(move(0),move(1))))
//  println(board.getTile(Point(1,3)))
//  println(board.avaliableMoves(Point(1,3)))
//  val move = Move(Point(1,1), Point(3,1),)

}