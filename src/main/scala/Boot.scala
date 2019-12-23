import java.nio.file.Paths

import com.ebuson.chess.controller.{Board, Game}
import com.whitehatgaming.UserInputFile

object Boot extends App {

  println("Hello chess!")

  val stepsPath = Paths.get("./src/main/resources/data/in-check.txt").toAbsolutePath
  val input = new UserInputFile(stepsPath.toString)

  val board = new Board().init
  val game = new Game(input).start
}
