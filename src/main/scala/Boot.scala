import java.nio.file.Paths

import com.whitehatgaming.chess.models.Board.Point
import com.whitehatgaming.UserInputFile
import com.whitehatgaming.chess.controller.{Board, Game}

object Boot extends App {

  println("Hello chess!")

  val stepsPath = Paths.get("./src/main/resources/gameover.txt").toAbsolutePath
    val input = new UserInputFile(stepsPath.toString)

  val board =new Board()
  board.init
  val game = new Game(input)
  game.start
}
