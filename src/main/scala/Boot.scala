import com.whitehatgaming.chess.models.Board.{Board, Point}
import com.whitehatgaming.UserInputFile
import com.whitehatgaming.chess.models.Game.{Game, Move}

object Boot extends App {

  println("Hello chess!")

  val input = new UserInputFile("/run/media/thug/OLD-REPOS/swvl/chess-demo/src/test/scala/data/checkmate.txt")

  val game = new Game(input)
  game.start
}