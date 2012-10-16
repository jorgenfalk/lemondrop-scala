package lemondrops

abstract class Cell {
  /**
   * A wall means that the cell is regarded as an obstacle that is impossible to walk through.
   * @return
   */
  def isWall: Boolean = weight == 100

  /**
   * The cell has no resistance what so ever. Zero friction, ful speed ahead passage, wroom, wroom....
   * @return
   */
  def isEmpty: Boolean = weight == 0

  val weight: Int

  def r(d: Int): Int = d*100/(100 - weight)
}