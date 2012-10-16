package lemondrops

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import lemondrops.AStarSolver._

@RunWith(classOf[JUnitRunner])
class AStarSolverTest extends FunSuite {

  test("Path Finder Scenario") {
    val b: Array[Array[String]] = Array(
      Array("X", " ", " " , " "),
      Array("X", " ", "X" , " "),
      Array("X", " ", "X" , " "),
      Array(" ", " ", "X" , " ")
    )
    val expected: Array[Array[String]] = Array(
      Array("X", "5", "6" , "7"),
      Array("X", "4", "X" , "8"),
      Array("X", "3", "X" , "9"),
      Array("1", "2", "X" , "10")
    )

    val board: Board[Dummy] = Board[String, Dummy](b, s => new Dummy(s))
    val solver: AStarSolver[Dummy] = AStarSolver[Dummy](manhattan)
    val path:List[Position] = solver.shortestPath(board, new Position(0,3), new Position(3,3))

    print("Path    : "); path.foreach(p => print("(" + p + ") ")) ; println()
    print("Expected: "); pathFromMap(expected).foreach(p => print("(" + p + ") ")) ; println()

    assert(path.length === 10, "Path length should be 10")
    assert(path(6) === new Position(3,0), "7:th cell (index 6) should be at position 3,0")
    assert(path.sameElements(pathFromMap(expected)), "Should has same path as in expected map")
  }

  test("Manhattan map") {
    val b = Array (
      Array(" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", " ", " ", "X", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", " ", " ", "X", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", "X", "X", " ", " ", " "),
      Array(" ", " ", " ", " ", "X", "X", " ", " ", " ", " ", " "),
      Array(" ", "X", "X", " ", "X", " ", " ", " ", "X", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", " ", "X", "X", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", "X", " ", " ", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ")
    )

    val expected = Array(
      Array(" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "),
      Array(" ", " ", "1", "2", " ", " ", " ", " ", "X", " ", " "),
      Array(" ", " ", " ", "3", " ", " ", " ", " ", "X", " ", " "),
      Array(" ", " ", " ", "4", " ", " ", "X", "X", " ", " ", " "),
      Array(" ", " ", " ", "5", "X", "X", " ", " ", " ", " ", " "),
      Array(" ", "X", "X", "6", "X", " ", " ", " ", "X", " ", " "),
      Array(" ", " ", " ", "7", "8", "9", " ", "X", "X", " ", " "),
      Array(" ", " ", " ", " ", " ", "10", "X", " ", " ", " ", " "),
      Array(" ", " ", " ", " ", " ", "11", "12", "13", "14", "15", " "),
      Array(" ", " ", " ", " ", " ", "  ", "  ", "  ", "  ", "16", " ")
    )

    val board: Board[Dummy] = Board[String, Dummy](b, s => new Dummy(s))
    val solver: AStarSolver[Dummy] = AStarSolver[Dummy](manhattan)
    val path:List[Position] = solver.shortestPath(board, new Position(2,2), new Position(9,10))

    print("Path    : "); path.foreach(p => print("(" + p + ") ")) ; println()
    print("Expected: "); pathFromMap(expected).foreach(p => print("(" + p + ") ")) ; println()

    assert(path.length === 16, "Path length should be 16")
    assert(path.sameElements(pathFromMap(expected)), "Should has same path as in expected map")
  }

  test("diagonal map, distance heuristics") {
    val b = Array (
      Array(" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", " ", " ", "X", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", " ", " ", "X", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", "X", "X", "X", " ", " "),
      Array(" ", " ", " ", " ", "X", "X", "X", " ", " ", " ", " "),
      Array(" ", " ", "X", "X", "X", " ", " ", " ", "X", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", "X", "X", "X", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", "X", " ", " ", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", "X", " ", " ", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", "X", " ", " ", " ", " ")
    )

    val expected = Array(
      Array(" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", " ", " ", "6", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", " ", "5", "X", "7", " "),
      Array(" ", " ", " ", " ", " ", " ", "4", " ", "X", "8", " "),
      Array(" ", " ", " ", "1", "2", "3", "X", "X", "X", "9", " "),
      Array(" ", " ", " ", " ", "X", "X", "X", " ", " ", "10", " "),
      Array(" ", " ", "X", "X", "X", " ", " ", " ", "X", "11", " "),
      Array(" ", " ", " ", " ", " ", " ", "X", "X", "X", "12", " "),
      Array(" ", " ", " ", " ", " ", " ", "X", "14", "13", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", "X", " ", " ", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", "X", " ", " ", " ", " ")
    )

    val board: Board[Dummy] = Board[String, Dummy](b, s => new Dummy(s))
    val solver: AStarSolver[Dummy] = AStarSolver[Dummy](diagonalDist)
    val path:List[Position] = solver.shortestPath(board, new Position(3,4), new Position(7,8))

    print("Path    : "); path.foreach(p => print("(" + p + ") ")) ; println()
    print("Expected: "); pathFromMap(expected).foreach(p => print("(" + p + ") ")) ; println()

    assert(path.length === 14, "Path length")
    assert(path.sameElements(pathFromMap(expected)), "Should has same path as in expected map")
  }

  test("diagonal map2, distance heuristics") {
    val b = Array (
      Array(" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", " ", " ", "X", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", " ", " ", "X", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", "X", "X", " ", " ", " "),
      Array(" ", " ", " ", " ", "X", "X", "X", " ", " ", " ", " "),
      Array(" ", "X", "X", "X", "X", " ", "X", " ", "X", " ", " "),
      Array(" ", "X", " ", "X", " ", " ", "X", "X", " ", " ", " "),
      Array(" ", "X", " ", " ", "X", " ", " ", " ", " ", " ", " "),
      Array(" ", "X", "X", "X", "X", " ", "X", " ", " ", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", "X", " ", " ", " ", " ")
    )

    val expected = Array(
      Array(" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", " ", " ", "X", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", " ", " ", "X", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", "X", "X", " ", " ", " "),
      Array(" ", "3", "2", "1", "X", "X", "X", " ", " ", " ", " "),
      Array("4", "X", "X", "X", "X", " ", "X", " ", "X", " ", " "),
      Array("5", "X", "16", "X", "14", " ", "X", "X", " ", " ", " "),
      Array("6", "X", " ", "15", "X", "13", " ", " ", " ", " ", " "),
      Array("7", "X", "X", "X", "X", "12", "X", " ", " ", " ", " "),
      Array(" ", "8", "9", "10", "11", " ", "X", " ", " ", " ", " ")
    )

    val board: Board[Dummy] = Board[String, Dummy](b, s => new Dummy(s))
    val solver: AStarSolver[Dummy] = AStarSolver[Dummy](diagonalDist)
    val path:List[Position] = solver.shortestPath(board, new Position(3,5), new Position(2,7))

    print("Path    : "); path.foreach(p => print("(" + p + ") ")) ; println()
    print("Expected: "); pathFromMap(expected).foreach(p => print("(" + p + ") ")) ; println()

    assert(path.length === 16, "Path length")
    assert(path.sameElements(pathFromMap(expected)), "Should has same path as in expected map")
  }

  test("diagonal map2, step heuristics") {
    val b = Array (
      Array(" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", " ", " ", "X", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", " ", " ", "X", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", "X", "X", " ", " ", " "),
      Array(" ", " ", " ", " ", "X", "X", "X", " ", " ", " ", " "),
      Array(" ", "X", "X", "X", "X", " ", "X", " ", "X", " ", " "),
      Array(" ", "X", " ", "X", " ", " ", "X", "X", " ", " ", " "),
      Array(" ", "X", " ", " ", "X", " ", " ", " ", " ", " ", " "),
      Array(" ", "X", "X", "X", "X", " ", "X", " ", " ", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", "X", " ", " ", " ", " ")
    )

    val expected = Array(
      Array(" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", " ", " ", "X", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", "4", "5", "X", " ", " "),
      Array(" ", " ", " ", " ", "2", "3", "X", "X", "6", " ", " "),
      Array(" ", " ", " ", "1", "X", "X", "X", "7", " ", " ", " "),
      Array(" ", "X", "X", "X", "X", " ", "X", "8", "X", " ", " "),
      Array(" ", "X", "15", "X", "13", " ", "X", "X", "9", " ", " "),
      Array(" ", "X", " ", "14", "X", "12", "11", "10", " ", " ", " "),
      Array(" ", "X", "X", "X", "X", " ", "X", " ", " ", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", "X", " ", " ", " ", " ")
    )

    val board: Board[Dummy] = Board[String, Dummy](b, s => new Dummy(s))
    val solver: AStarSolver[Dummy] = AStarSolver[Dummy](diagonalStep)
    val path:List[Position] = solver.shortestPath(board, new Position(3,5), new Position(2,7))

    print("Path    : "); path.foreach(p => print("(" + p + ") ")) ; println()
    print("Expected: "); pathFromMap(expected).foreach(p => print("(" + p + ") ")) ; println()

    assert(path.length === 15, "Path length")
    assert(path.sameElements(pathFromMap(expected)), "Should has same path as in expected map")
  }

  test("Manhattan map with obstacles") {
    val b = Array (
      Array(" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", " ", " ", "X", " ", " "),
      Array(" ", "X", "B", "X", "X", " ", " ", " ", "X", " ", " "),
      Array(" ", " ", " ", " ", "X", " ", "X", "X", " ", " ", " "),
      Array(" ", " ", " ", " ", "X", "X", " ", " ", " ", " ", " "),
      Array(" ", "C", "X", "A", "X", " ", " ", " ", "X", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", " ", "X", "X", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", "X", " ", " ", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ")
    )

    val expected = Array(
      Array(" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "),
      Array(" ", " ", "2", "1", " ", " ", " ", " ", "X", " ", " "),
      Array(" ", "X", "3", "X", "X", " ", " ", " ", "X", " ", " "),
      Array(" ", " ", "4", " ", "X", " ", "X", "X", " ", " ", " "),
      Array(" ", "6", "5", " ", "X", "X", " ", " ", " ", " ", " "),
      Array(" ", "7", "X", "X", "X", " ", " ", " ", "X", " ", " "),
      Array(" ", "8", "9", "10", "11", " ", " ", "X", "X", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", "X", " ", " ", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "),
      Array(" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ")
    )

    val board: Board[Dummy] = Board[String, Dummy](b, s => new Dummy(s))
    val solver: AStarSolver[Dummy] = AStarSolver[Dummy](manhattan)
    val path:List[Position] = solver.shortestPath(board, new Position(3,2), new Position(4,7))

    print("Path    : "); path.foreach(p => print("(" + p + ") ")) ; println()
    print("Expected: "); pathFromMap(expected).foreach(p => print("(" + p + ") ")) ; println()

    assert(path.length === 11, "Path length")
    assert(path.sameElements(pathFromMap(expected)), "Should has same path as in expected map")
  }

  class Dummy(val stone: String) extends Cell{
    val weight = stone match {
      case "X" => 100
      case "A" => 90
      case "B" => 50
      case "C" => 10
      case _ => 0
    }
  }

  def pathFromMap(ss: Array[Array[String]]): List[Position] = {
    Board[String, Dummy](ss, s => new Dummy(s)).board.flatMap(dd => dd.flatMap(d =>
      if (d.t.stone.forall(_.isDigit)) Some(d) else None
    )).toList.sortBy(d => d.t.stone.toInt).map(d => d.position)
  }
}
