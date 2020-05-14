import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.effect.DropShadow
import scalafx.scene.layout.HBox
import scalafx.scene.paint.Color._
import scalafx.scene.paint.{LinearGradient, Stops}
import scalafx.scene.text.Text
import ui.SimulationBoardScene

object SimulationApp extends JFXApp {

  private val WIDTH = 800
  private val HEIGHT = 800

  stage = new PrimaryStage {
    title = "Epidemic Simulator"
    width = WIDTH
    height = HEIGHT
    scene = new SimulationBoardScene()
  }
}
