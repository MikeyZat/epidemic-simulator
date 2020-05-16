package ui

import scalafx.geometry.Insets
import scalafx.scene.chart.{LineChart, PieChart}
import scalafx.scene.layout.{BorderPane, Pane, VBox}
import simulation.Simulator

class SimulationPane(population: Long, initSick: Int, incidenceRate: Double, mortality: Double, diseaseDuration: Int) extends Pane {

  println(population, initSick, incidenceRate, mortality, diseaseDuration)

  val simulator = new Simulator(
    population = population,
    initSick = initSick,
    incidenceRate = incidenceRate,
    mortality = mortality,
    diseaseDuration = diseaseDuration,
    HEIGHT = 400,
    WIDTH = 400
  )

  val paintingManager: SimulationPaintingManager = SimulationPaintingManager(simulator)


  val boardPane = new BorderPane
  val pointsPane = new Pane
  paintingManager.simulator.patients
    .map(p => new PatientPointPainter(p))
    .map(p => p.paint())
    .foreach(p => pointsPane.children.add(p))

  val chartBox: VBox = new VBox {
    prefWidth = 500
    prefHeight = 800
    spacing = 100
    padding = Insets(top = 30, bottom = 30, left = 50, right = 50)
  }

  val visualizationBox: VBox = new VBox {
    prefWidth = 600
    prefHeight = 800
    spacing = 100
    padding = Insets(top = 30, bottom = 30, left = 50, right = 50)
  }
  val dailyChart: LineChart[Number, Number] = SimulationLineChart("Daily cases", paintingManager.dailyChartSequence)
  val totalChart: LineChart[Number, Number] = SimulationLineChart("Total cases", paintingManager.totalChartSequence)
  val proportionChart: PieChart = SimulationPieChart("Total cases proportions", paintingManager.totalPieChart)

  chartBox.children.addAll(
    proportionChart,
    dailyChart,
  )
  visualizationBox.children.addAll(
    pointsPane,
    totalChart
  )

  boardPane.left = visualizationBox
  boardPane.right = chartBox
  children.add(boardPane)
}
