package ui

import scalafx.collections.ObservableBuffer
import scalafx.scene.chart.{LineChart, NumberAxis, XYChart}
import javafx.scene.chart.XYChart.Data


// default series colors
// 1 - green
// 2 - red
// 3 - white
// 4 - gray
// series param -> sequence of tuples containing series name and observable buffer of data points
object SimulationLineChart  {

  def apply(title: String, series: Seq[(String, ObservableBuffer[Data[Number, Number]])]): LineChart[Number, Number] = {
    val xAxis = NumberAxis()
    xAxis.label = title
    val yAxis = NumberAxis()
    val chart = LineChart(xAxis, yAxis)

    series foreach {
      case (name, buffer) =>
        val s = XYChart.Series[Number, Number](name, buffer)
        chart.getData.add(s)
    }

    chart.lookup(".default-color0.chart-symbol")
    chart
  }






}
