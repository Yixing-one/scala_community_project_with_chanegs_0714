package dotty.tools
package dotc.util

/** A class for the reading part of mutable or immutable maps.
 */
abstract class ReadOnlyMap[Key, Value] extends Pure:

  def lookup(x: Key): Value | Null

  def size: Int

  def iterator: Iterator[(Key, Value)]
  def keysIterator: Iterator[Key]
  def valuesIterator: Iterator[Value]

  def isEmpty: Boolean = size == 0

  def get(key: Key): Option[Value] = lookup(key) match
    case null => None
    case v => Some(v.uncheckedNN)

  def getOrElse(key: Key, value: => Value) = lookup(key) match
    case null => value
    case v => v.uncheckedNN

  def contains(key: Key): Boolean = lookup(key) != null

  def apply(key: Key): Value = lookup(key) match
    case null => throw new NoSuchElementException(s"$key")
    case v => v.uncheckedNN

  def toArray: Array[(Key, Value)] =
    val result = new Array[(Key, Value)](size)
    var idx = 0
    for pair <- iterator do
      result(idx) = pair
      idx += 1
    result

  def toSeq: Seq[(Key, Value)] = toArray.toSeq

