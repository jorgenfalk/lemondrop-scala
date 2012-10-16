package lemondrops

class Node[T](val t:T, val x:Int, val y:Int) extends Ordered[Node[T]]{
  var g:Int = Int.MaxValue
  var h:Int = 0
  def f:Int = if (g == Int.MaxValue || h == Int.MaxValue) Int.MaxValue else g + h
  var parent: Node[T] = null

  def reset(p: Position, heuristics: (Position, Position) => Int) {
    g = Int.MaxValue
    h = heuristics(this.position, p)
  }

  def reset() {
    g = Int.MaxValue
    h = 0
  }

  def linkWithParent(node: Node[T], distance: Int): Node[T] = {
    this.g = node.g + distance
    this.parent = node

    this
  }

  def position: Position = new Position(x, y)


  override def compare(that: Node[T]):Int = {
      if ((this.f > that.f) ||
        (this.f == that.f && this.g > that.g) ||
        (this.f == that.f && this.g == that.g && this.x < that.x) ||
        (this.f == that.f && this.g == that.g && this.x == that.x && this.y < that.y)) 1
      else if (this.f == that.f && this.g == that.g && this.x == that.x && this.y == that.y) 0
      else -1
  }

  override def toString = "[pos: ("+ x + "," + y + "), g:"+ g + ", h:"+ h + ", f:" + (if(f == Int.MaxValue) "∞" else f) + "] "
}
