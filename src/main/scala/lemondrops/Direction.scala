package lemondrops

/**
 * Class for defining the possible different direction from a given position in a map and the corresponding distance.
 *
 * @param x the difference in x for a direction. Usually this is either 1, 0 or -1
 * @param y the difference in y for a direction  Usually this is either 1, 0 or -1
 * @param d the distance (or 'weight') for this direction. This is usually 1 for 'Manhattan' type directions and
 *          10 or 14 if we allow diagonal directions (14 is approximately sqrt(2) * 10)
 */
case class Direction(val x:Int, val y:Int, val d:Int)
