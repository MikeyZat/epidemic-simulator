package ui

import scala.collection.mutable.ArrayDeque

class PatientPointsManager {
  val points: ArrayDeque[PatientPointPainter] = new ArrayDeque()
}
