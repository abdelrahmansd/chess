import java.nio.file.Paths

import com.whitehatgaming.UserInputFile
import com.whitehatgaming.chess.controller.{Board, Game}

object Boot extends App {

  println("Hello chess!")

  val stepsPath = Paths.get("./src/main/resources/data/in-check.txt").toAbsolutePath
  val input = new UserInputFile(stepsPath.toString)

  val board = new Board().init
  val game = new Game(input).start
}
