package simulation

object PatientCondition extends Enumeration {
  type PatientCondition = Value
  val Healthy, Sick, Dead, Recovered = Value
}
