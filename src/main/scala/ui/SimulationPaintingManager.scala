package ui

import javafx.scene.chart.{PieChart, XYChart}
import scalafx.animation.AnimationTimer
import scalafx.collections.ObservableBuffer
import simulation.{DailyStatistics, Simulator, TotalStatistics}

class SimulationPaintingManager private(
  val simulator: Simulator,
  val dailyChartSequence: Seq[(String, ObservableBuffer[XYChart.Data[Number, Number]])],
  val totalChartSequence: Seq[(String, ObservableBuffer[XYChart.Data[Number, Number]])],
  val totalPieChart: ObservableBuffer[PieChart.Data]
) {

  var currentDay: Int = 1

  var lastTime = 0L
  val animTimer = AnimationTimer(t => {
    if (lastTime > 0) {
      val delta = (t - lastTime) / 1e9
      if (delta > SimulationPaintingManager.delta) {
        simulateDay()
        lastTime = t
      }
    } else {
      lastTime = t
    }
  })
  animTimer.start()


  def simulateDay(): Unit = {
    simulator.simulateDay()
    val dailyStatistics = simulator.dailyStats
    val totalStatistics = simulator.totalStats

    val DailyStatistics(dead, recovered, infected) = dailyStatistics
    val TotalStatistics(healthyTotal, deadTotal, recoveredTotal, infectedTotal) = totalStatistics

    dailyChartSequence foreach {
      case ("Recovered", recoveredDailyBuffer) =>
        addPointToLineChartDataBuffer(recoveredDailyBuffer, (currentDay, recovered))
      case ("Infected",infectedDailyBuffer) =>
        addPointToLineChartDataBuffer(infectedDailyBuffer, (currentDay, infected))
      case ("Dead", deadDailyBuffer) =>
        addPointToLineChartDataBuffer(deadDailyBuffer, (currentDay, dead))
    }

    totalChartSequence foreach {
      case ("Healthy", healthyTotalBuffer) =>
        addPointToLineChartDataBuffer(healthyTotalBuffer, (currentDay, healthyTotal))
      case ("Infected", infectedTotalBuffer) =>
      addPointToLineChartDataBuffer(infectedTotalBuffer, (currentDay, infectedTotal))
      case ("Recovered", recoveredTotalBuffer) =>
        addPointToLineChartDataBuffer(recoveredTotalBuffer, (currentDay, recoveredTotal))
      case ("Dead", deadTotalBuffer) =>
        addPointToLineChartDataBuffer(deadTotalBuffer, (currentDay, deadTotal))
    }

    updatePieChartDataBuffer(totalPieChart, Seq(
      ("Healthy", healthyTotal),
      ("Infected", infectedTotal),
      ("Recovered", recoveredTotal),
      ("Dead", deadTotal)
    ))

    currentDay += 1
  }
}


object SimulationPaintingManager {

  def apply(simulator: Simulator): SimulationPaintingManager = {
    simulator.simulateDay()

    val dailyStatistics = simulator.dailyStats
    val totalStatistics = simulator.totalStats

    val DailyStatistics(dead, recovered, infected) = dailyStatistics
    val Seq(recoveredDailyBuffer, deadDailyBuffer, infectedDailyBuffer) = Seq(recovered, dead, infected) map {
      value: Long => createLineChartDataBuffer(Seq((0L, value)))
    }

    val dailyChartSequence = Seq(
      ("Recovered", recoveredDailyBuffer),
      ("Infected",infectedDailyBuffer),
      ("Dead", deadDailyBuffer)
    )

    val TotalStatistics(healthyTotal, deadTotal, recoveredTotal, infectedTotal) = totalStatistics

    val Seq(healthyTotalBuffer, infectedTotalBuffer, recoveredTotalBuffer, deadTotalBuffer) = Seq(healthyTotal, infectedTotal, recoveredTotal, deadTotal) map {
      value: Long => createLineChartDataBuffer(Seq((0L, value)))
    }

    val totalChartSequence = Seq(
      ("Healthy", healthyTotalBuffer),
      ("Infected", infectedTotalBuffer),
      ("Recovered", recoveredTotalBuffer),
      ("Dead", deadTotalBuffer)
    )

    val totalPieChart  = createPieChartDataBuffer(Seq(
      ("Healthy", healthyTotal),
      ("Infected", infectedTotal),
      ("Recovered", recoveredTotal),
      ("Dead", deadTotal)
    ))

    new SimulationPaintingManager(simulator, dailyChartSequence, totalChartSequence, totalPieChart)
  }

  val delta: Long = 1
}
