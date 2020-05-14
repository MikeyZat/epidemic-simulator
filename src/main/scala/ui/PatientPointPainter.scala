package ui

import javafx.scene.paint
import scalafx.beans.property.ObjectProperty
import scalafx.event.subscriptions.Subscription
import scalafx.scene.paint.{Color, Paint}
import scalafx.scene.shape
import scalafx.scene.shape.Circle
import simulation.PatientState

// there is some kind of problem when trying to bind color property to fill
// see: https://github.com/scalafx/scalafx/issues/14
class PatientPointPainter(val state: PatientState) {
  var conditionSub: Subscription = _

  def paint(): Circle  = {
    val circle = new Circle()
    circle.centerX <== state.x
    circle.centerY <== state.y
    circle.radius = PatientPoint.radius
    circle.fill = getPatientConditionColor(state.condition())

    conditionSub = state.condition.onChange {
      (_, _, newValue) =>
        circle.fill = getPatientConditionColor(newValue)
    }

    circle
  }

}

object PatientPoint {
  val radius: Int = 3
}
