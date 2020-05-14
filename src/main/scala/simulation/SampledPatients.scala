package simulation

case class SampledPatients(deadOrRecoveredCount: Long, newInfectedCount: Long) {

  def +(that: SampledPatients): SampledPatients = SampledPatients(
    this.deadOrRecoveredCount + that.deadOrRecoveredCount,
    this.newInfectedCount + that.newInfectedCount
  )

  def *(ratio: Double): SampledPatients = SampledPatients((this.deadOrRecoveredCount * ratio).toLong, (this.newInfectedCount * ratio).toLong)
}
