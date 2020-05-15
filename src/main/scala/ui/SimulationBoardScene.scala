package ui

import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.layout.{BorderPane, Pane, VBox}
import scalafx.scene.paint.Color._

class SimulationBoardScene(val patientPointsManager: PatientPointsManager) extends Scene {
  fill = Black

  val boardPane = new BorderPane
  val pointsPane = new Pane
  patientPointsManager.simulator.patients
    .map(p => new PatientPointPainter(p))
    .map(p => p.paint())
    .foreach(p => pointsPane.children.add(p))

  val chartBox = new VBox {
    prefWidth = 500
    prefHeight = 800
    spacing = 100
    padding = Insets(top = 30, bottom = 30, left = 50, right = 50)
  }

  val visualizationBox = new VBox {
    prefWidth = 600
    prefHeight = 800
    spacing = 100
    padding = Insets(top = 30, bottom = 30, left = 50, right = 50)
  }
  val dailyChart = SimulationLineChart("Daily cases", patientPointsManager.dailyChartSequence)
  val totalChart = SimulationLineChart("Total cases", patientPointsManager.totalChartSequence)
  val proportionChart = SimulationPieChart("Total cases proportions", patientPointsManager.totalPieChart)

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

  content = boardPane
  stylesheets.add("line_chart.css")
}
