import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.effect.DropShadow
import scalafx.scene.layout.HBox
import scalafx.scene.paint.Color._
import scalafx.scene.paint.{LinearGradient, Stops}
import scalafx.scene.text.Text
import simulation.{PatientCondition, PatientState}
import ui.{PatientPointsManager, SimulationBoardScene}


object MockPatientStates {

  private val rand = scala.util.Random

  private def getRandom(to: Int): Int = rand.nextInt(to)


  def patients(): List[PatientState] = (1 to 1000).map(_ =>
    new PatientState(
      getRandom(400),
      getRandom(400),
      rand.shuffle(PatientCondition.values.toList).head)
  ).toList
}

object SimulationApp extends JFXApp {

  val WIDTH = 1200
  val HEIGHT = 900

  val patientManager: PatientPointsManager = new PatientPointsManager().addPoints(MockPatientStates.patients())

  stage = new PrimaryStage {
    title = "Epidemic Simulator"
    width = WIDTH
    height = HEIGHT
    scene = new SimulationBoardScene(patientManager)
  }
}
