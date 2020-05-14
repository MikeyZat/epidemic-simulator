package ui

import simulation.PatientState

import scala.collection.mutable.ArrayDeque

class PatientPointsManager() {
  val points: ArrayDeque[PatientPointPainter] = new ArrayDeque()

  def addPoint(state: PatientState): PatientPointsManager = {
    points.addOne(new PatientPointPainter(state))
    this
  }

  def addPoints(states: List[PatientState]): PatientPointsManager = {
    states.map(state => points.addOne(new PatientPointPainter(state)))
    this
  }
}
