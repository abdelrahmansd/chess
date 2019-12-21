import com.whitehatgaming.chess.models.Board.Point
import com.whitehatgaming.UserInputFile
import com.whitehatgaming.chess.controller.{Board, Game}

object Boot extends App {

  println("Hello chess!")

  val input = new UserInputFile("/run/media/thug/OLD-REPOS/swvl/chess-demo/src/test/scala/data/checkmate.txt")

  val board =new Board()
  board.init
  val game = new Game(input)
  game.start
}
