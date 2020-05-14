package ui

import scalafx.animation.AnimationTimer
import simulation.{PatientCondition, PatientState}

import scala.collection.mutable.ArrayDeque

class PatientPointsManager() {
  val points: ArrayDeque[PatientPointPainter] = new ArrayDeque()
  var lastTime = 0L
  val animTimer = AnimationTimer(t => {
    if (lastTime > 0) {
      val delta = (t - lastTime) / 1e9
      if (delta > 5) {
        for (p <- points) {
          p.state.condition.value = util.Random.shuffle(PatientCondition.values.toList).head
//          if (p.state.condition() == PatientCondition.Sick) {
//            p.state.condition.value = PatientCondition.Healthy
//
//          }
        }
        lastTime = t
      }
    } else {
      lastTime = t
    }
  })
  animTimer.start()

  def addPoint(state: PatientState): PatientPointsManager = {
    points.addOne(new PatientPointPainter(state))
    this
  }

  def addPoints(states: List[PatientState]): PatientPointsManager = {
    states.map(state => points.addOne(new PatientPointPainter(state)))
    this
  }
}
