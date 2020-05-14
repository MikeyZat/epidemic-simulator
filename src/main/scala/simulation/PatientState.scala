package simulation

import scalafx.beans.property.{DoubleProperty, IntegerProperty, ObjectProperty}
import simulation.PatientCondition.PatientCondition

// this is the class where we want to keep all patient state
// it will be great if we use http://www.scalafx.org/docs/properties/ so to keep GUI reactive
// all simulation action should be propagated via patient state
// example of properties: patient condition (healthy, sick, dead, recovered), position,
class PatientState(xPos: Int, yPos: Int, startCondition: PatientCondition) {
  val x: DoubleProperty = DoubleProperty(xPos)
  val y: DoubleProperty = DoubleProperty(yPos)
  val condition: ObjectProperty[PatientCondition] = new ObjectProperty[PatientCondition](this, "condition", startCondition)

}
