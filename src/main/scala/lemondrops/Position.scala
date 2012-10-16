package lemondrops

/**
 *
 */
final class Position(val x:Int, val y:Int)  {
  def +(d: Direction): Position = new Position(x + d.x, y + d.y)

  override def toString = "x:" + x + " ,y:" + y

  override def hashCode() = 31 * (31 + x) + y

  override def equals(p:  Any) = p match {
    case that:Position => this.x == that.x && this.y == that.y
    case _ => false
  }
}
