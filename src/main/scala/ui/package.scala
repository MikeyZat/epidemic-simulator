import javafx.scene.chart.{PieChart, XYChart}
import scalafx.collections.ObservableBuffer
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


  def createLineChartDataBuffer(series: Seq[(Long, Long)]): ObservableBuffer[XYChart.Data[Number, Number]] = {
    val data = series map {
      case (x, y) => new XYChart.Data[Number, Number](x, y)
    }
    ObservableBuffer(data)
  }

  def addPointToLineChartDataBuffer(buffer: ObservableBuffer[XYChart.Data[Number, Number]], point: (Long, Long)): ObservableBuffer[XYChart.Data[Number, Number]] = {
    val (x, y) = point
    buffer.add(new XYChart.Data[Number, Number](x, y))
    buffer
  }

  def createPieChartDataBuffer(series: Seq[(String, Long)]): ObservableBuffer[PieChart.Data] = {
    val data = series map {
      case (name, value) => new PieChart.Data(name, value)
    }
    ObservableBuffer(data)
  }

  def updatePieChartDataBuffer(buffer: ObservableBuffer[PieChart.Data], updatedSeries: Seq[(String, Long)]): ObservableBuffer[PieChart.Data] = {
    updatedSeries foreach {
      case (name, value) =>
        buffer.find(p => p.getName == name) match {
          case Some(d) =>
            val idx = buffer.indexOf(d)
            buffer.update(idx, new PieChart.Data(name, value))
          case None =>
            buffer.add(new PieChart.Data(name, value))
        }
    }
    buffer
  }


}
