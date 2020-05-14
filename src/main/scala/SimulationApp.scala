import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.effect.DropShadow
import scalafx.scene.layout.HBox
import scalafx.scene.paint.Color._
import scalafx.scene.paint.{LinearGradient, Stops}
import scalafx.scene.text.Text
import simulation.Simulator
import ui.{PatientPointsManager, SimulationBoardScene}

object SimulationApp extends JFXApp {

  val WIDTH = 800
  val HEIGHT = 800

  val simulator = new Simulator(10000, 10, 3.0, 0.1, 7)

  val patientManager: PatientPointsManager = new PatientPointsManager().addPoints(simulator.patients)

  stage = new PrimaryStage {
    title = "Epidemic Simulator"
    width = WIDTH
    height = HEIGHT
    scene = new SimulationBoardScene(patientManager)
  }
}
