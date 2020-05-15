package simulation

import simulation.PatientCondition.PatientCondition
import scala.math.min

class Simulator(
                 population: Long,
                 initSick: Int,
                 incidenceRate: Double,
                 mortality: Double,
                 diseaseDuration: Int,
                 HEIGHT: Int = 800, WIDTH: Int = 800,
                 N: Int = 1000
               ) {
  val scaleRatio: Double = N.toDouble / population.toDouble

  val patients: List[PatientState] = (1 to N).map(_ =>
    new PatientState(
      getRandomInt(WIDTH),
      getRandomInt(HEIGHT),
      PatientCondition.Healthy)
  ).toList

  // init sick patients
  patients.slice(0, (initSick * scaleRatio).toInt) foreach {
    (p: PatientState) => p.condition.value = PatientCondition.Sick
  }

  val epidemicParams: EpidemicParams = EpidemicParams(incidenceRate, mortality, diseaseDuration)

  var dailyStats: DailyStatistics = DailyStatistics(0, 0, initSick)

  var totalStats: TotalStatistics = TotalStatistics(population - initSick, 0, 0, initSick)

  private def setPatientValues(list: List[PatientState], from: Int, to: Int, newValue: PatientCondition): Unit = {
    list.slice(from, to) foreach { p => p.condition.value = newValue }
  }

  private def updatePatients(): Unit = {
    val healthyPatients = this.patients filter { p => p.condition() == PatientCondition.Healthy }
    val sickPatients = this.patients filter { p => p.condition() == PatientCondition.Sick }
    val alreadyDead = this.patients count{ p => p.condition() == PatientCondition.Dead }
    val alreadyRecovered = this.patients count { p => p.condition() == PatientCondition.Recovered }

    val newDeaths = (this.totalStats.dead * scaleRatio).toInt - alreadyDead
    val newRecoveries = (this.totalStats.recovered * scaleRatio).toInt - alreadyRecovered
    val newInfections = (this.totalStats.infected * scaleRatio).toInt - sickPatients.length

    setPatientValues(sickPatients, 0, newDeaths, PatientCondition.Dead)
    setPatientValues(sickPatients, newDeaths, newDeaths + newRecoveries, PatientCondition.Recovered)
    setPatientValues(healthyPatients, 0, newInfections, PatientCondition.Sick)
  }

  private def updateTotalStats(): Unit = {
    totalStats = TotalStatistics(
      totalStats.healthy - dailyStats.infected,
      totalStats.dead + dailyStats.dead,
      totalStats.recovered + dailyStats.recovered,
      totalStats.infected + dailyStats.infected - (dailyStats.dead + dailyStats.recovered)
    )
  }

  def simulateDay(): Unit = {
    val newDailyStats: DailyStatistics = SimulationService.getNewDailyStatistics(this.totalStats.infected, epidemicParams)
    this.dailyStats = DailyStatistics(newDailyStats.dead, newDailyStats.recovered, min(newDailyStats.infected, totalStats.healthy))
    this.updateTotalStats()
    this.updatePatients()
  }
}
