package com.ebuson.chess.controller

import com.ebuson.chess.models.Board.{Point, Tile}
import com.ebuson.chess.models.Game.Move
import com.ebuson.chess.models.Game.Move.MoveResponse
import com.ebuson.chess.models.piece._
import com.ebuson.chess.models.piece.rules.DirectionType
import com.ebuson.chess.models.piece.rules.DirectionType.Vertical

class Board() {

  val rowCount = 8
  val colCount = 8
  var tiles = Array.ofDim[Tile](rowCount, colCount)

  def init: Unit = {
    // init White Pieces
    tiles(7)(0) = Tile(coordinates = Point(7, 0), piece = Some(Rook(false)))
    tiles(7)(1) = Tile(coordinates = Point(7, 1), piece = Some(KNight(false)))
    tiles(7)(2) = Tile(coordinates = Point(7, 2), piece = Some(Bishop(false)))
    tiles(7)(3) = Tile(coordinates = Point(7, 3), piece = Some(Queen(false)))
    tiles(7)(4) = Tile(coordinates = Point(7, 4), piece = Some(King(false)))
    tiles(7)(5) = Tile(coordinates = Point(7, 5), piece = Some(Bishop(false)))
    tiles(7)(6) = Tile(coordinates = Point(7, 6), piece = Some(KNight(false)))
    tiles(7)(7) = Tile(coordinates = Point(7, 7), piece = Some(Rook(false)))

    // init White Pawns
    for (i <- 0 to 7) {
      tiles(6)(i) = Tile(coordinates = Point(6, i), piece = Some(Pawn(false)))
    }

    // init Black Pieces
    tiles(0)(0) = Tile(coordinates = Point(0, 0), piece = Some(Rook(true)))
    tiles(0)(1) = Tile(coordinates = Point(0, 1), piece = Some(KNight(true)))
    tiles(0)(2) = Tile(coordinates = Point(0, 2), piece = Some(Bishop(true)))
    tiles(0)(3) = Tile(coordinates = Point(0, 3), piece = Some(Queen(true)))
    tiles(0)(4) = Tile(coordinates = Point(0, 4), piece = Some(King(true)))
    tiles(0)(5) = Tile(coordinates = Point(0, 5), piece = Some(Bishop(true)))
    tiles(0)(6) = Tile(coordinates = Point(0, 6), piece = Some(KNight(true)))
    tiles(0)(7) = Tile(coordinates = Point(0, 7), piece = Some(Rook(true)))

    // init Black Pawns
    for (i <- 0 to 7) {
      tiles(1)(i) = Tile(coordinates = Point(1, i), piece = Some(Pawn(true)))
    }

    // init other tiles
    for (i <- 2 to 5) {
      for (j <- 0 to 7) {
        tiles(i)(j) = Tile(coordinates = Point(i, j), piece = None)
      }
    }
  }

  def render(killedPieces: List[Piece] = List.empty) = {
    println("\tA\t  \tB\t \tC\t \tD\t \tE\t \tF\t \tG\t \tH\t")
    println("   -----------------------------------------------------------------------------------------------------------------------------")
    var count = 9
    for (row <- tiles) {
      print(s"${count -= 1; count} |")
      for (tile <- row) {
        tile.piece match {
          case Some(piece) if piece.isBlack == true => print(Console.BLUE + "\t" + piece.toString.split("\\(")(0) + Console.WHITE + "\t|")
          case Some(piece) if piece.isBlack == false => print(Console.WHITE + "\t" + piece.toString.split("\\(")(0) + "\t|")
          case _ => print("\t\t|")
        }
      }
      println()
      println("   -----------------------------------------------------------------------------------------------------------------------------")
    }
    if (!killedPieces.isEmpty) print("Killed Pieces: ")
    killedPieces foreach (piece => print(s"${piece} "))
    println()
  }

  def isValidMove(move: Move): Boolean = {
    val currentTile = getTile(move.current)
    if (currentTile.isDefined && currentTile.get.piece.isDefined && currentTile.get.piece.get.isBlack != move.player.isBlack) return false // invalid player
    val validTiles = getAvailableMoves(move.current)
    val validPoints = validTiles map (t => t.coordinates)
    validPoints.contains(move.next)
  }

  def move(move: Move): MoveResponse = {
    val currentTile = getTile(move.current).get
    currentTile.piece.get match {
      case piece: Pawn if piece.isFirstMove => piece.isFirstMove = false
      case _ => print("")
    }
    val nextTile = getTile(move.next).get
    val killedPiece = nextTile.piece
    nextTile.piece = currentTile.piece
    currentTile.reset
    MoveResponse(killedPiece)
  }

  def getTile(point: Point): Option[Tile] = {
    if ((point.x <= rowCount - 1 && point.x >= 0) && (point.y <= colCount - 1 && point.y >= 0)) {
      Some(tiles(point.x)(point.y))
    } else None
  }

  def undoMove(move: Move, moveRes: MoveResponse) = {
    val currentTile = getTile(move.current).get
    val nextTile = getTile(move.next).get

    nextTile.piece.get match {
      case piece: Pawn if !piece.isFirstMove => piece.isFirstMove = true
      case _ => print("")
    }
    currentTile.piece = nextTile.piece
    nextTile.piece = moveRes.killedPiece
  }

  def isKingSafe(isBlack: Boolean) = {
    val opponentTiles = tiles flatMap (tilesRow => tilesRow.filter(tile => tile.piece.isDefined && tile.piece.get.isBlack != isBlack))
    !(opponentTiles exists (tile => isKingChecked(tile.coordinates)))
  }

  def isKingChecked(point: Point): Boolean = {
    val piece = getTile(point).get.piece.get
    val validTiles = getAvailableMoves(point)
    validTiles exists (t => (t.piece.isDefined && t.piece.get.toString.contains(Piece.KING)) && (t.piece.get.isBlack != piece.isBlack))
  }

  def getAvailableMoves(point: Point): List[Tile] = {
    val tile = getTile(point).get
    var validTiles: List[Tile] = List.empty[Tile]
    tile.piece match {
      case Some(piece: Pawn) => {
        def validateTile(point: Point) = {
          val nextTile = getTile(point)
          if (nextTile.isDefined && nextTile.get.piece.isEmpty) validTiles :+= nextTile.get
        }

        def validateEnemyTile(point: Point) = {
          val nextTile = getTile(point)
          if (nextTile.isDefined && nextTile.get.piece.isDefined && !nextTile.get.piece.get.isBlack) validTiles :+= nextTile.get
        }

        if (piece.isBlack) { // black
          validateTile(Point(tile.coordinates.x + 1, tile.coordinates.y))
          if (piece.isFirstMove) validateTile(Point(tile.coordinates.x + 2, tile.coordinates.y))

          validateEnemyTile(Point(tile.coordinates.x + 1, tile.coordinates.y + 1)) // down right
          validateEnemyTile(Point(tile.coordinates.x + 1, tile.coordinates.y - 1)) // down left
        } else { // white
          validateTile(Point(tile.coordinates.x - 1, tile.coordinates.y))
          if (piece.isFirstMove) validateTile(Point(tile.coordinates.x - 2, tile.coordinates.y))

          validateEnemyTile(Point(tile.coordinates.x - 1, tile.coordinates.y + 1)) // up right
          validateEnemyTile(Point(tile.coordinates.x - 1, tile.coordinates.y - 1)) // up left
        }
      }

      case Some(piece: Piece) => {
        def validateTile(point: Point) = {
          val nextTile = getTile(point)
          val opponentFound = nextTile.isDefined && (nextTile.get.piece.isDefined && nextTile.get.piece.get.isBlack != piece.isBlack)
          if (nextTile.isDefined && (nextTile.get.piece.isEmpty || opponentFound)) validTiles :+= nextTile.get
          nextTile.isDefined && !nextTile.get.piece.isDefined
        }

        piece.directionTypes.foreach(directionType => {
          directionType match {
            case Vertical => {
              DirectionType.Vertical.availableMoves foreach (pair => {
                var checkNextTile = true
                var x = point.x
                do {
                  checkNextTile = validateTile(Point({
                    x += pair._1;
                    x
                  }, point.y))
                } while (checkNextTile && piece.isMultiStep)
              })
            }

            case DirectionType.Horizontal => {
              DirectionType.Horizontal.availableMoves foreach (pair => {
                var checkNextTile = true
                var y = point.y
                do {
                  checkNextTile = validateTile(Point(point.x, {
                    y += pair._2;
                    y
                  }))

                } while (checkNextTile && piece.isMultiStep)
              })
            }

            case DirectionType.Diagonal => {
              DirectionType.Diagonal.availableMoves foreach (pair => {
                var checkNextTile = true
                var x = point.x
                var y = point.y
                do {
                  checkNextTile = validateTile(Point({
                    x += pair._1;
                    x
                  }, {
                    y += pair._2;
                    y
                  }))
                } while (checkNextTile && piece.isMultiStep)
              })
            }

            case DirectionType.LShape => {
              DirectionType.LShape.availableMoves foreach (pair => {
                val nextTile = getTile(Point(point.x + pair._1, point.y + pair._2))
                if (nextTile.isDefined && (nextTile.get.piece.isEmpty || (nextTile.get.piece.isDefined && nextTile.get.piece.get.isBlack != piece.isBlack))) validTiles :+= nextTile.get
              })
            }
          }
        })
      }
      case None => List.empty[Tile]
    }
    validTiles
  }
}

