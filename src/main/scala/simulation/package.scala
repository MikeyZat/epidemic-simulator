import scala.util.Random

package object simulation {
  val rand: Random.type = scala.util.Random

  def getRandomInt(to: Int): Int = rand.nextInt(to)
}
