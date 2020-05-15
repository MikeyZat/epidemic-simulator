package ui

import scalafx.collections.ObservableBuffer
import scalafx.scene.chart.PieChart
import javafx.scene.chart.PieChart.Data



// default series colors
// 1 - green
// 2 - red
// 3 - white
// 4 - gray
object SimulationPieChart {
  def apply(title: String, series: ObservableBuffer[Data]): PieChart = {
    val chart = PieChart(series)
    chart.setTitle(title)

    chart.lookup(".default-color0.chart-symbol")
    chart
  }
}
