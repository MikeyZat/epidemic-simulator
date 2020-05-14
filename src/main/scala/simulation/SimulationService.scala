package simulation

import java.util.concurrent.ThreadLocalRandom
import scala.annotation.tailrec

case class SampledPatients(finished: Int, newInfections: Int)

object SimulationService {

  private def getFinishedAndInfectedPatients(patientsCount: Int, diseaseEndProbability: Double, infectOthersProbability: Double): SampledPatients = {
    getSampledPatients(patientsCount, 0, 0, diseaseEndProbability, infectOthersProbability)
  }

  @tailrec
  private def getSampledPatients(iteration: Long, end_acc: Int, infect_acc: Int, end_prob: Double, infect_prob: Double): SampledPatients = {
    if (iteration == 0)
      SampledPatients(end_acc, infect_acc)
    else {
      val u = ThreadLocalRandom.current().nextDouble()
      getSampledPatients(
        iteration - 1,
        if (u < end_prob) end_acc + 1 else end_acc,
        if (u < infect_prob) infect_acc + 1 else infect_acc,
        end_prob,
        infect_prob
      )
    }
  }

  private def getNewDeaths(sickPatientsCount: Int, mortalityRate: Double): Int = {
    getSampledDeaths(sickPatientsCount, 0, mortalityRate)
  }

  @tailrec
  private def getSampledDeaths(iteration: Long, deaths_acc: Int, death_prob: Double): Int = {
    if (iteration == 0)
      deaths_acc
    else {
      val u = ThreadLocalRandom.current().nextDouble()
      getSampledDeaths(iteration - 1, if (u < death_prob) deaths_acc + 1 else deaths_acc, death_prob)
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
