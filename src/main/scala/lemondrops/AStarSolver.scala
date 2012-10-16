package lemondrops

import collection.immutable.TreeSet

class AStarSolver[T <: Cell](val directions: Set[Direction], val heuristics:(Position, Position) => Int) {
  private def reverse(node: Node[T], accu: List[Position]): List[Position] =
    if (node == null) accu else reverse(node.parent, node.position :: accu)

  def shortestPath(board: Board[T], from: Position, to: Position): List[Position] = {
    def pathEmptyFrom(node: Node[T], d: Direction): Boolean =
      board.containsPosition(node.position + d) && !board.nodeAt(node.position + d).t.isWall && node.g + board.nodeAt(node.position + d).t.r(d.d) < board.nodeAt(node.position + d).g

    def newUnvisitedNodesFromNodeInDirections(head: Node[T], dir: Set[Direction]): Set[Node[T]] =
      dir.flatMap(d => if (pathEmptyFrom(head, d)) Some(board.nodeAt(head.position + d).linkWithParent(head, board.nodeAt(head.position + d).t.r(d.d))) else None)

    def find(hasReachedGoal: Node[T] => Boolean, unvisited: Set[Node[T]]):Option[Node[T]] = {
      if (unvisited.isEmpty) None
      else if (hasReachedGoal(unvisited.head)) Some(unvisited.head)
      else find(hasReachedGoal, unvisited.tail ++ newUnvisitedNodesFromNodeInDirections(unvisited.head, directions))
    }

    board.prepareForGoal(to, heuristics)
    val start = board.nodeAt(from)
    val goal = board.nodeAt(to)
    start.g = 0

    find(_.equals(goal), TreeSet.empty[Node[T]] + start) match {
      case Some(node) => reverse(node, List.empty)
      case None => List.empty
    }
  }
}

object AStarSolver {
  def hManhattan:(Position, Position) => Int = {case (p1,p2) => math.abs(p1.x - p2.x) + math.abs(p1.y - p2.y)}
  def hDiagonalDist:(Position, Position) => Int = {case (p1,p2) => {val dx = math.abs(p1.x - p2.x); val dy = math.abs(p1.y - p2.y); math.abs(dx - dy)*10 + math.min(dx,dy)*14}}
  def hDiagonalStep:(Position, Position) => Int = {case (p1,p2) => {val dx = math.abs(p1.x - p2.x); val dy = math.abs(p1.y - p2.y); math.max(dx,dy)}}
  case class Type(val directions: Set[Direction], val heuristics: (Position, Position) => Int)
  val manhattan = new Type(Set(Direction(0,1,1), Direction(1,0,1), Direction(0,-1,1), Direction(-1,0,1)), hManhattan)
  val diagonalDist = new Type(Set(Direction(0,1,10), Direction(1,0,10), Direction(0,-1,10), Direction(-1,0,10),
                     Direction(1,1,14), Direction(1,-1,14), Direction(-1,-1,14), Direction(-1,1,14)), hDiagonalDist)
  val diagonalStep = new Type(Set(Direction(0,1,1), Direction(1,0,1), Direction(0,-1,1), Direction(-1,0,1),
                       Direction(1,1,1), Direction(1,-1,1), Direction(-1,-1,1), Direction(-1,1,1)), hDiagonalStep)
  def apply[T <: Cell](t: Type) = new AStarSolver[T](t.directions, t.heuristics)
}