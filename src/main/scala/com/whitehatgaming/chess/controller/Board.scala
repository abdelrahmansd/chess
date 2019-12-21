package com.whitehatgaming.chess.controller

import com.whitehatgaming.chess.models.Board.{Point, Tile}
import com.whitehatgaming.chess.models.Game.Move
import com.whitehatgaming.chess.models.Game.Move.MoveResponse
import com.whitehatgaming.chess.models.piece._
import com.whitehatgaming.chess.models.piece.rules.DirectionType

class Board() {

  var tiles = Array.ofDim[Tile](8, 8)

  def init: Unit = {
//    print("\u001b[2J") // clear console
    // init White Pieces
    tiles(7)(0) = Tile(coordinates = Point(7 , 0), piece = Some(Rook(false)))
    tiles(7)(1) = Tile(coordinates = Point(7 , 1), piece = Some(KNight(false)))
    tiles(7)(2) = Tile(coordinates = Point(7 , 2), piece = Some(Bishop(false)))
    tiles(7)(3) = Tile(coordinates = Point(7 , 3), piece = Some(Queen(false)))
    tiles(7)(4) = Tile(coordinates = Point(7 , 4), piece = Some(King(false)))
    tiles(7)(5) = Tile(coordinates = Point(7 , 5), piece = Some(Bishop(false)))
    tiles(7)(6) = Tile(coordinates = Point(7 , 6), piece = Some(KNight(false)))
    tiles(7)(7) = Tile(coordinates = Point(7 , 7), piece = Some(Rook(false)))

    // init White Pawns
    for(i <- 0 to 7) {
      tiles(6)(i) = Tile(coordinates = Point(6 , i), piece = Some(Pawn(false)))
    }


    // init Black Pieces
    tiles(0)(0) = Tile(coordinates = Point(0 , 0), piece = Some(Rook(true)))
    tiles(0)(1) = Tile(coordinates = Point(0 , 1), piece = Some(KNight(true)))
    tiles(0)(2) = Tile(coordinates = Point(0 , 2), piece = Some(Bishop(true)))
    tiles(0)(3) = Tile(coordinates = Point(0 , 3), piece = Some(Queen(true)))
    tiles(0)(4) = Tile(coordinates = Point(0 , 4), piece = Some(King(true)))
    tiles(0)(5) = Tile(coordinates = Point(0 , 5), piece = Some(Bishop(true)))
    tiles(0)(6) = Tile(coordinates = Point(0 , 6), piece = Some(KNight(true)))
    tiles(0)(7) = Tile(coordinates = Point(0 , 7), piece = Some(Rook(true)))

    // init Black Pawns
    for(i <- 0 to 7) {
      tiles(1)(i) = Tile(coordinates = Point(1 , i), piece = Some(Pawn(true)))
    }

    // init other tiles
    for( i <- 2 to 5){
      for(j <- 0 to 7) {
        tiles(i)(j) = Tile(coordinates = Point(i , j), piece = None)
      }
    }
//    tiles(4)(4) = Tile(coordinates = Point(4 , 4), piece = Some(King(true)))


  }

  def render = {

    println("--------------------------------")
    for ( row <- tiles) {
      for (tile <- row) {
        tile.piece match {
          case Some(piece) if piece.isBlack == true  => print(Console.BLUE + " " + piece.toString.head + Console.WHITE +" |")
          case Some(piece) if piece.isBlack == false  => print(Console.WHITE + " " + piece.toString.head + " |")
          case _ => print("   |")
        }
      }
      println()
      println("--------------------------------")

    }
  }

  def getTile(point:Point): Option[Tile] = {
    if((point.x <= 7 && point.x >=0) && (point.y <= 7 && point.y >=0)){
      Some(tiles(point.x)(point.y))
    } else None

  }

  def getAvaliableMoves(point: Point): List[Tile] = {
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
        if(piece.isBlack){ // black
          validateTile(Point(tile.coordinates.x + 1, tile.coordinates.y))
          if(piece.isFirstMove) validateTile(Point(tile.coordinates.x + 2, tile.coordinates.y))

          validateEnemyTile(Point(tile.coordinates.x + 1, tile.coordinates.y + 1)) // down right
          validateEnemyTile(Point(tile.coordinates.x + 1, tile.coordinates.y - 1)) // down left
        } else { // white
          validateTile(Point(tile.coordinates.x - 1, tile.coordinates.y))
          if(piece.isFirstMove) validateTile(Point(tile.coordinates.x - 2, tile.coordinates.y))

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
              case DirectionType.Vertical => {
                DirectionType.Vertical.availableMoves foreach(pair => {
                  var checkNextTile = true
                  var x = point.x + pair._1
                  do {
                    checkNextTile = validateTile(Point(x, point.y))
                    x += pair._1
                  } while (checkNextTile && piece.isMultiStep)
                })
              }

              case DirectionType.Horizontal => {
                DirectionType.Horizontal.availableMoves foreach(pair => {
                  var checkNextTile = true
                  var y = point.y + pair._2
                  do {
                    checkNextTile = validateTile(Point(point.x, y))
                    y += pair._2
                  } while (checkNextTile && piece.isMultiStep)
                })
              }

              case DirectionType.Diagonal => {
                DirectionType.Diagonal.availableMoves foreach( pair => {
                  var checkNextTile = true
                  var x = point.x
                  var y = point.y
                  do {
                    checkNextTile = validateTile(Point({x += pair._1; x}, {y += pair._2; y}))
                  } while (checkNextTile && piece.isMultiStep)
                })
              }

              case DirectionType.LShape => {
                DirectionType.LShape.availableMoves foreach(pair => {
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

  def move(move: Move): MoveResponse = {
    val currentTile = getTile(move.current).get
    val validTiles = getAvaliableMoves(move.current)

    val validPoints = validTiles map (t => t.coordinates)
    val isValidMove: Boolean = validPoints.contains(move.next)
    if (isValidMove) {
      currentTile.piece.get match {
        case piece: Pawn if piece.isFirstMove => piece.isFirstMove = false
        case _ => print()
      }
      val nextTile = getTile(move.next).get

      nextTile.piece = currentTile.piece
      currentTile.reset
      MoveResponse(isValidMove, nextTile.piece)
    } else MoveResponse(isValidMove, None)
  }

  def isKingChecked(point: Point): Boolean = {
    val piece = getTile(point).get.piece.get
    println(piece)
    val validTiles = getAvaliableMoves(point)
    println(validTiles)
    val res = validTiles exists ( t => {
      println((t.piece.isDefined && t.piece.get.toString == Piece.KING))
      println((t.piece.isDefined && t.piece.get.isBlack != piece.isBlack))
      (t.piece.isDefined && t.piece.get.toString == Piece.KING) && (t.piece.get.isBlack != piece.isBlack)
    })
  println(res)
    res
  }
}

