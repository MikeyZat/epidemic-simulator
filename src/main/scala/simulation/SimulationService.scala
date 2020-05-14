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
    val scaleRatio = max(iterations / MAX_SAMPLES, 1)
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

  def getNewDailyStatistics(sickPatientsCount: Int, epidemicParams: EpidemicParams): DailyStatistics = {
    val diseaseEndProbability: Double = 1.0 / epidemicParams.diseaseDuration
    val infectOthersProbability: Double = epidemicParams.incidenceRate / epidemicParams.diseaseDuration
    val sampledPatients: SampledPatients = getFinishedAndInfectedPatients(sickPatientsCount, diseaseEndProbability, infectOthersProbability)
    val deaths: Long = getNewDeaths(sampledPatients.deadOrRecoveredCount, epidemicParams.mortality)
    DailyStatistics(
      dead = deaths,
      recovered = sampledPatients.deadOrRecoveredCount - deaths,
      infected = sampledPatients.newInfectedCount
    )
  }
}
