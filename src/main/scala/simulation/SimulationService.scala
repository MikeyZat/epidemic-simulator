package simulation

import java.util.concurrent.ThreadLocalRandom
import scala.annotation.tailrec
import scala.math._
import scala.collection.parallel.CollectionConverters._

object SimulationService {
  val MAX_SAMPLES = 1000000
  val N = 4


  private def getFinishedAndInfectedPatients(patientsCount: Long, diseaseEndProbability: Double, infectOthersProbability: Double): SampledPatients = {
    getScaledSampledData(patientsCount, (batchSize: Int, scaleRatio: Double) => {
      val batches = (1 to N).toList.par map { _ =>
        getSampledData(
          batchSize,
          (u: Double, data: SampledPatients) => SampledPatients(
            if (u < diseaseEndProbability) data.deadOrRecoveredCount + 1 else data.deadOrRecoveredCount,
            if (u < infectOthersProbability) data.newInfectedCount + max(1, infectOthersProbability.toInt) else data.newInfectedCount
          ),
          SampledPatients(0, 0)
        )
      }
      batches.reduce(_ + _) * scaleRatio
    })
  }

  private def getNewDeaths(sickPatientsCount: Long, mortalityRate: Double): Long = {
    getScaledSampledData(sickPatientsCount, (batchSize: Int, scaleRatio: Double) => {
      val batches = (1 to N).toList.par map { _ =>
        getSampledData(
          batchSize,
          (u: Double, deathsCount: Int) => if (u < mortalityRate) deathsCount + 1 else deathsCount,
          0
        )
      }
      (batches.sum * scaleRatio).toLong
    })
  }

  private def getScaledSampledData[T](iterations: Long, samplingFunction: (Int, Double) => T): T = {
    val scaledIterations = min(iterations, MAX_SAMPLES)
    val scaleRatio: Double = max(iterations / MAX_SAMPLES.toDouble, 1.0)
    val batchSize = (scaledIterations / N).toInt
    samplingFunction(batchSize, scaleRatio)
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

  private def getReducedInfectionProbability(probability: Double, sickToHealthyRate: Double, parametricFunction: Double => Double): Double = {
     probability * (1 - parametricFunction(sickToHealthyRate))
  }

  def getNewDailyStatistics(sickPatientsCount: Long, healthyPeopleCount: Long, epidemicParams: EpidemicParams): DailyStatistics = {
    val sickToHealthyRate: Double = sickPatientsCount.toDouble / healthyPeopleCount
    val diseaseEndProbability: Double = 1.0 / epidemicParams.diseaseDuration
    val infectOthersProbability: Double = getReducedInfectionProbability(
      probability = epidemicParams.incidenceRate / epidemicParams.diseaseDuration,
      sickToHealthyRate = sickToHealthyRate,
      parametricFunction = (x: Double) => (-exp(-x)) + 1.0
    )
    val sampledPatients: SampledPatients = getFinishedAndInfectedPatients(sickPatientsCount, diseaseEndProbability, infectOthersProbability)
    val deaths: Long = getNewDeaths(sampledPatients.deadOrRecoveredCount, epidemicParams.mortality)
    DailyStatistics(
      dead = deaths,
      recovered = sampledPatients.deadOrRecoveredCount - deaths,
      infected = sampledPatients.newInfectedCount
    )
  }
}
