package ui
import scalafx.scene.Scene
import scalafx.scene.paint.Color._

class SimulationBoardScene(val patientPointsManager: PatientPointsManager) extends Scene {
  fill = Black
  content = patientPointsManager.points.map(p => p.paint())
}
