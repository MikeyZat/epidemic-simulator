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


  def patients(): List[PatientState] = List(
    new PatientState(getRandom(800), getRandom(600), rand.shuffle(PatientCondition.values.toList).head),
    new PatientState(getRandom(800), getRandom(600), rand.shuffle(PatientCondition.values.toList).head),
    new PatientState(getRandom(800), getRandom(600), rand.shuffle(PatientCondition.values.toList).head),
    new PatientState(getRandom(800), getRandom(600), rand.shuffle(PatientCondition.values.toList).head),
    new PatientState(getRandom(800), getRandom(600), rand.shuffle(PatientCondition.values.toList).head),
    new PatientState(getRandom(800), getRandom(600), rand.shuffle(PatientCondition.values.toList).head),
  )


}

object SimulationApp extends JFXApp {

  val WIDTH = 800
  val HEIGHT = 800

  val patientManager: PatientPointsManager = new PatientPointsManager().addPoints(MockPatientStates.patients())

  stage = new PrimaryStage {
    title = "Epidemic Simulator"
    width = WIDTH
    height = HEIGHT
    scene = new SimulationBoardScene(patientManager)
  }
}
