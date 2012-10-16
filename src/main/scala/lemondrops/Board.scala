package lemondrops

import scala.Array

/**
 *
 */
class Board[T <: Cell](map: Array[Array[T]]) {
  def containsPosition(position: Position): Boolean = position.x >= 0 && map.length > position.x && position.y >= 0 && map(0).length > position.y

  def nodeAt(position: Position): Node[T] = board(position.y)(position.x)

  var board:Array[Array[Node[T]]] = map.zipWithIndex.map{ case (r,x) => r.zipWithIndex.map { case(c,y) => new Node[T](c, y, x)} }

  def reset() {
    board.foreach( r => r.foreach( n => n.reset() ) )
  }

  def prepareForGoal(position: Position, h:(Position, Position) => Int){
    board.foreach( r => r.foreach( n => n.reset(position, h) ) )
  }
}

object Board {
  def apply[T, U <: Cell: ClassManifest](arr: Array[Array[T]], f:T=>U):Board[U] = new Board[U](arr.map( a => a.map(f) ))
}

