import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import simulation.Simulator
import ui.{PatientPointsManager, SimulationBoardScene}

object SimulationApp extends JFXApp {

  val WIDTH = 1200
  val HEIGHT = 900

  val simulator = new Simulator(
    population = 10000,
    initSick = 10,
    incidenceRate = 3.0,
    mortality = 0.1,
    diseaseDuration = 7,
    HEIGHT = 400,
    WIDTH = 400
  )

  val patientManager: PatientPointsManager = PatientPointsManager(simulator)

  stage = new PrimaryStage {
    title = "Epidemic Simulator"
    width = WIDTH
    height = HEIGHT
    scene = new SimulationBoardScene(patientManager)
  }
}
