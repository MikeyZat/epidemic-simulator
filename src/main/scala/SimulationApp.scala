import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import simulation.Simulator
import ui.{SimulationPaintingManager, SimulationBoardScene}

object SimulationApp extends JFXApp {

  val WIDTH = 1200
  val HEIGHT = 900


  stage = new PrimaryStage {
    title = "Epidemic Simulator"
    width = WIDTH
    height = HEIGHT
    scene = new SimulationBoardScene()
  }
}
