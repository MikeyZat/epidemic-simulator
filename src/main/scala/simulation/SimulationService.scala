package simulation

import java.util.concurrent.ThreadLocalRandom
import scala.annotation.tailrec
import scala.collection.parallel.CollectionConverters._

case class SampledPatients(finished: Int, newInfections: Int)

object SimulationService {

  val N = 4

  private def getFinishedAndInfectedPatients(patientsCount: Int, diseaseEndProbability: Double, infectOthersProbability: Double): SampledPatients = {
    val iterations = patientsCount / N
    val sums = (1 to N).toList.par map {_ => getSampledData(
      iterations,
      (u: Double, data: SampledPatients) => SampledPatients(
        if (u < diseaseEndProbability) data.finished + 1 else data.finished,
        if (u < infectOthersProbability) data.newInfections + 1 else data.newInfections
      ),
      SampledPatients(0, 0)
    )}
    sums.reduce((x, y) => SampledPatients(x.finished + y.finished, x.newInfections + y.newInfections))
  }

  private def getNewDeaths(sickPatientsCount: Int, mortalityRate: Double): Int = {
    val iterations = sickPatientsCount / N
    val sums = (1 to N).toList.par map {_ => getSampledData(
      iterations,
      (u: Double, deathsCount: Int) => if (u < mortalityRate) deathsCount + 1 else deathsCount,
      0
    )}
    sums.sum
  }

  @tailrec
  private def getSampledData[T](iteration: Long, updateFunction: (Double, T) => T, data: T): T = {
    if (iteration == 0)
      data
    else {
      val u = ThreadLocalRandom.current().nextDouble()
      getSampledData(
        iteration - 1,
        updateFunction,
        updateFunction(u, data)
      )
    }
  }

  def getNewStats(sickPatientsCount: Int, epidemicParams: EpidemicParams): NextDayResults = {
    val diseaseEndProbability: Double = 1.0 / epidemicParams.diseaseDuration
    val infectOthersProbability: Double = epidemicParams.incidenceRate / epidemicParams.diseaseDuration
    val sampledPatients: SampledPatients = getFinishedAndInfectedPatients(sickPatientsCount, diseaseEndProbability, infectOthersProbability)
    val deaths: Int = getNewDeaths(sampledPatients.finished, epidemicParams.mortality)
    NextDayResults(sampledPatients.newInfections, deaths, sampledPatients.finished - deaths)
  }

}
