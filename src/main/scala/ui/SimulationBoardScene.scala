package ui
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.layout.{Background, BackgroundFill, BorderPane, CornerRadii, Pane, VBox}
import scalafx.scene.paint.Color._
import scalafx.scene.shape.Rectangle

class SimulationBoardScene(val patientPointsManager: PatientPointsManager) extends Scene {
  fill = Black

  val boardPane = new BorderPane
  val pointsPane = new Pane
  patientPointsManager.points
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


  val serieOne = ui.createLineChartDataBuffer(Seq(
    (1, 3),
    (2, 16),
    (3, 6),
    (4, 20),
    (5, 25),
  ))
  val serieTwo= ui.createLineChartDataBuffer(Seq(
    (1, 17),
    (2, 5),
    (3, 6),
    (4, 11),
    (5, 20),
  ))


  val pieSerie = ui.createPieChartDataBuffer(Seq(("Healthy", 125), ("Dead", 400)))



  val lineChart = SimulationLineChart("Epidemia growth per day", Seq(("Healthy", serieOne), ("Dead", serieTwo)))
  val lineChartTwo = SimulationLineChart("Total cases worldwide", Seq(("Healthy", serieOne), ("Dead", serieTwo)))
  val pieChart = SimulationPieChart("Total of all", pieSerie)

  chartBox.children.addAll(
    pieChart,
    lineChart,
  )
  visualizationBox.children.addAll(
    pointsPane,
    lineChartTwo

  )

  boardPane.left = visualizationBox
  boardPane.right = chartBox

  content = boardPane
  stylesheets.add("line_chart.css")
}
