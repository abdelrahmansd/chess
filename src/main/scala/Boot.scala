import com.whitehatgaming.chess.models.Board.{Board, Point}
import com.whitehatgaming.chess.models.Game.Move

object Boot extends App {

  println("Hello chess!")

  val board = new Board()
  board.init
  board.render
  println(board.avaliableMoves(Point(1,0)))
//  val move = Move(Point(1,1), Point(3,1),)

}