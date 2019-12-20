package com.whitehatgaming.chess.models.Board

import scala.util.control.Breaks._
import com.whitehatgaming.chess.models.Game.Move
import com.whitehatgaming.chess.models.piece._
import com.whitehatgaming.chess.models.piece.rules.DirectionType

import scala.util.control.Breaks

class Board() {

  var tiles = Array.ofDim[Tile](8, 8)
//  var tile = Tile(Point())
//  var tiles = Array.fill(8,8)(" ")

  def init: Unit = {

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
      tiles(1)(i) = Tile(coordinates = Point(1 , i), piece = None)
//      tiles(1)(i) = Tile(coordinates = Point(1 , i), piece = Some(Pawn(true)))
    }

    for( i <- 2 to 5){
      for(j <- 0 to 7) {
        tiles(i)(j) = Tile(coordinates = Point(i , j), piece = None)
      }
    }

//    tiles(2)(1) = Tile(coordinates = Point(2 , 1), piece = Some(Pawn(false)))
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
    // validate valid position
    if((point.x <= 7 && point.x >=0) && (point.y <= 7 && point.y >=0)){
      Some(tiles(point.x)(point.y))
    } else None

  }

  def avaliableMoves(point: Point): List[Tile] = {
    val tile = getTile(point).get
    var validTiles: List[Tile] = List.empty[Tile]
     tile.piece match {
      case Some(piece: Pawn) => {
        if(piece.isBlack){

          var nextTile = getTile(Point(tile.coordinates.x + 1, tile.coordinates.y))
          if (nextTile.isDefined && nextTile.get.piece.isEmpty) validTiles :+= nextTile.get

          if(piece.isFirstMove) {
            val nextTile = getTile(Point(tile.coordinates.x + 2, tile.coordinates.y))
            if (nextTile.isDefined && nextTile.get.piece.isEmpty) validTiles :+= nextTile.get
          }

          nextTile = getTile(Point(tile.coordinates.x + 1, tile.coordinates.y + 1))
          if (nextTile.isDefined && nextTile.get.piece.isDefined && !nextTile.get.piece.get.isBlack) validTiles :+= nextTile.get

          nextTile = getTile(Point(tile.coordinates.x + 1, tile.coordinates.y - 1))
          if (nextTile.isDefined && nextTile.get.piece.isDefined && !nextTile.get.piece.get.isBlack) validTiles :+= nextTile.get



        } else { // white
          var nextTile = getTile(Point(tile.coordinates.x - 1, tile.coordinates.y))
          if (nextTile.isDefined && nextTile.get.piece.isEmpty) validTiles :+= nextTile.get

          if(piece.isFirstMove) {
            val nextTile = getTile(Point(tile.coordinates.x - 2, tile.coordinates.y))
            if (nextTile.isDefined && nextTile.get.piece.isEmpty) validTiles :+= nextTile.get
          }

          nextTile = getTile(Point(tile.coordinates.x - 1, tile.coordinates.y + 1))
          if (nextTile.isDefined && nextTile.get.piece.isDefined && !nextTile.get.piece.get.isBlack) validTiles :+= nextTile.get

          nextTile = getTile(Point(tile.coordinates.x - 1, tile.coordinates.y - 1))
          if (nextTile.isDefined && nextTile.get.piece.isDefined && !nextTile.get.piece.get.isBlack) validTiles :+= nextTile.get



        }
      }
      case Some(piece: Piece) => {
          piece.directionTypes.map(directionType => {
            directionType match {
              case DirectionType.Vertical => {
                val loop = new Breaks
                loop.breakable {
                  for (x <- point.x to 7) { // up to down
                    val nextTile = getTile(Point(x, point.y))
                    val checkNextTile = nextTile.isDefined && nextTile.get.piece.isDefined && nextTile.get.piece.get.isBlack != piece.isBlack
                    if (nextTile.isDefined && (nextTile.get.piece.isEmpty || checkNextTile)) validTiles :+= nextTile.get
                    if (checkNextTile) loop.break
                  }
                }

                for(x <- point.x to 0 by -1 ) { // down to up
                  val nextTile = getTile(Point(x, point.y))
                  if (nextTile.isDefined && (nextTile.get.piece.isEmpty || (nextTile.get.piece.isDefined && nextTile.get.piece.get.isBlack != piece.isBlack))) validTiles :+= nextTile.get
                }
              }
              case DirectionType.Horizontal => {
                for(y <- point.y to 7 ) { // left to right
                  val nextTile = getTile(Point(point.x, y))
                  if (nextTile.isDefined && (nextTile.get.piece.isEmpty || (nextTile.get.piece.isDefined && nextTile.get.piece.get.isBlack != piece.isBlack))) validTiles :+= nextTile.get
                }

                for(y <- point.y to 0 by -1 ) { // right to left
                  val nextTile = getTile(Point(point.x, y))
                  if (nextTile.isDefined && (nextTile.get.piece.isEmpty || (nextTile.get.piece.isDefined && nextTile.get.piece.get.isBlack != piece.isBlack))) validTiles :+= nextTile.get
                }
              }
              case DirectionType.Diagonal => {
                var checkNextTile = true
                var x = point.x
                var y = point.y
                while (checkNextTile){ // down to right

                  val nextTile = getTile(Point({x += 1; x}, {y += 1; y}))
                  if (nextTile.isDefined && (nextTile.get.piece.isEmpty || (nextTile.get.piece.isDefined && nextTile.get.piece.get.isBlack != piece.isBlack))) validTiles :+= nextTile.get
                  else checkNextTile = false

                }
                checkNextTile = true
                x = point.x
                y = point.y
                while (checkNextTile){ // down to left

                  val nextTile = getTile(Point({x += 1; x}, {y -= 1; y}))
                  if (nextTile.isDefined && (nextTile.get.piece.isEmpty || (nextTile.get.piece.isDefined && nextTile.get.piece.get.isBlack != piece.isBlack))) validTiles :+= nextTile.get
                  else checkNextTile = false

                }
                checkNextTile = true
                x = point.x
                y = point.y
                while (checkNextTile){ // up to right

                  val nextTile = getTile(Point({x -= 1; x}, {y += 1; y}))
                  if (nextTile.isDefined && (nextTile.get.piece.isEmpty || (nextTile.get.piece.isDefined && nextTile.get.piece.get.isBlack != piece.isBlack))) validTiles :+= nextTile.get
                  else checkNextTile = false

                }

                checkNextTile = true
                x = point.x
                y = point.y
                while (checkNextTile){ // up to left

                  val nextTile = getTile(Point({x -= 1; x}, {y -= 1; y}))
                  if (nextTile.isDefined && (nextTile.get.piece.isEmpty || (nextTile.get.piece.isDefined && nextTile.get.piece.get.isBlack != piece.isBlack))) validTiles :+= nextTile.get
                  else checkNextTile = false

                }
              }
              case DirectionType.LShape => {
                var nextTile = getTile(Point(point.x + 2, point.y + 1)) // down right
                if (nextTile.isDefined && (nextTile.get.piece.isEmpty || (nextTile.get.piece.isDefined && nextTile.get.piece.get.isBlack != piece.isBlack))) validTiles :+= nextTile.get

                 nextTile = getTile(Point(point.x - 2, point.y + 1)) // down left
                if (nextTile.isDefined && (nextTile.get.piece.isEmpty || (nextTile.get.piece.isDefined && nextTile.get.piece.get.isBlack != piece.isBlack))) validTiles :+= nextTile.get

                nextTile = getTile(Point(point.x + 2, point.y - 1)) // up right
                if (nextTile.isDefined && (nextTile.get.piece.isEmpty || (nextTile.get.piece.isDefined && nextTile.get.piece.get.isBlack != piece.isBlack))) validTiles :+= nextTile.get

                nextTile = getTile(Point(point.x - 2, point.y - 1)) // up left
                if (nextTile.isDefined && (nextTile.get.piece.isEmpty || (nextTile.get.piece.isDefined && nextTile.get.piece.get.isBlack != piece.isBlack))) validTiles :+= nextTile.get


                nextTile = getTile(Point(point.x + 1, point.y - 2)) // right down
                if (nextTile.isDefined && (nextTile.get.piece.isEmpty || (nextTile.get.piece.isDefined && nextTile.get.piece.get.isBlack != piece.isBlack))) validTiles :+= nextTile.get

                nextTile = getTile(Point(point.x - 1, point.y - 2)) // left down
                if (nextTile.isDefined && (nextTile.get.piece.isEmpty || (nextTile.get.piece.isDefined && nextTile.get.piece.get.isBlack != piece.isBlack))) validTiles :+= nextTile.get

                nextTile = getTile(Point(point.x + 1, point.y + 2)) // right up
                if (nextTile.isDefined && (nextTile.get.piece.isEmpty || (nextTile.get.piece.isDefined && nextTile.get.piece.get.isBlack != piece.isBlack))) validTiles :+= nextTile.get

                nextTile = getTile(Point(point.x - 1, point.y + 2)) // left up
                if (nextTile.isDefined && (nextTile.get.piece.isEmpty || (nextTile.get.piece.isDefined && nextTile.get.piece.get.isBlack != piece.isBlack))) validTiles :+= nextTile.get


              }
            }
          })
      }
      case None => List.empty[Tile]
    }
    validTiles
  }
//  def move(move: Move): Boolean = {
//    getTile(move.current).piece match {
//      case(p: Pawn) => {
//        var validmoves: List[Move] = List()
//          if (p.isBlack){ // handling black pieces
//
//          }
//      }
//      case _ =>
//    }
//    true
//  }

}

