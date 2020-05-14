import scalafx.scene.paint.Color
import simulation.PatientCondition._
import scalafx.scene.paint.Color._

package object ui {
  def getPatientConditionColor(condition: PatientCondition) = condition match {
    case Healthy => Green
    case Sick => Red
    case Recovered => Color.rgb(182, 182, 182)
    case Dead => Color.rgb(69, 69, 69)
  }
}
