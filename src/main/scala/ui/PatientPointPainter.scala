package ui

import javafx.animation.Animation.Status
import scalafx.animation.{Animation, Interpolator, KeyFrame, Timeline}
import scalafx.beans.property.{BooleanProperty, DoubleProperty, ObjectProperty}
import scalafx.event.subscriptions.Subscription
import scalafx.scene.layout.{Pane, StackPane, TilePane}
import scalafx.scene.paint.{Color, CycleMethod, Paint, RadialGradient, Stop}
import scalafx.scene.shape
import scalafx.scene.shape.Circle
import simulation.{PatientCondition, PatientState}
import scalafx.Includes._

import scala.language.postfixOps

class PatientPointPainter (val state: PatientState) {

  var conditionSub: Subscription = _
  var dangerCircle: Circle = createDangerCircle()
  var timeline: Timeline = createTimeline(dangerCircle)
  val finishAnimation: BooleanProperty = BooleanProperty(true);


  private def createDangerCircle(): Circle = {
    val circle = new Circle()
    circle.setVisible(false)

    circle.centerX = PatientPoint.dangerAreaRadius
    circle.centerY = PatientPoint.dangerAreaRadius
    circle.radius = PatientPoint.dangerAreaRadius

    circle.fill = RadialGradient(
      focusAngle = 0,
      focusDistance = 0.0,
      centerX = state.x(),
      centerY = state.y(),
      radius = 0,
      proportional =  false,
      cycleMethod = CycleMethod.NoCycle,
      stops = List(
        Stop(0.0, Color.rgb(255, 0, 0, 0.4)),
        Stop(1.0, Color.rgb(255, 0, 0, 0.1))
      )
    )

    circle
  }

  private def createTimeline(circle: Circle): Timeline = {
    val checkForFinishFrame = KeyFrame(0 s, onFinished = () => {
      if (finishAnimation()) {
        dangerCircle.setVisible(false)
        timeline.stop()
      }
    })

    new Timeline {
      cycleCount = Timeline.Indefinite
      autoReverse = true
      keyFrames = Seq(
      checkForFinishFrame,
      at (0 s) {circle.radius -> 0d tween Interpolator.EaseIn},
      at (1 s) {circle.radius -> PatientPoint.dangerAreaRadius tween Interpolator.EaseIn},
      )
    }
  }

  private def showDangerCircle() = {
    finishAnimation.value = false
    dangerCircle.setVisible(true)
    timeline.status() match {
      case Status.STOPPED => {
        timeline.playFromStart()
      }
      case Status.RUNNING =>
    }
  }

  private def hideDangerCircle() = {
    finishAnimation.value = true
  }

  def paint(): Pane  = {
    val pane = new StackPane()
    pane.layoutX <== state.x
    pane.layoutY <== state.y
    pane.prefWidth.value = PatientPoint.dangerAreaRadius * 2
    pane.prefHeight.value = PatientPoint.dangerAreaRadius * 2

    val circle = new Circle()
    circle.radius = PatientPoint.radius
    circle.fill = getPatientConditionColor(state.condition())
    pane.children.addAll(circle, dangerCircle)

    if (state.condition() == PatientCondition.Sick) {
      showDangerCircle()
    }

    conditionSub = state.condition.onChange {
      (_, _, newValue) =>
        circle.fill = getPatientConditionColor(newValue)

        if (newValue == PatientCondition.Sick) {
          showDangerCircle()
        } else {
          hideDangerCircle()
        }
    }

    pane
  }

}

object PatientPoint {
  val radius: Int = 3
  val dangerAreaRadius: Double = 25
}
