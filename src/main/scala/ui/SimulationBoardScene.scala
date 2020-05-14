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


  val lineChart = SimulationLineChart(null)
  chartBox.children.addAll(
    Rectangle(width = 500, height = 200, fill = White),
    lineChart
  )

  boardPane.left = pointsPane
  boardPane.right = chartBox

  content = boardPane
  stylesheets.add("line_chart.css")
}
