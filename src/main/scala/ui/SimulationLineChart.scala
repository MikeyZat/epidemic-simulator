package ui

import scalafx.collections.ObservableBuffer
import scalafx.scene.chart.{LineChart, NumberAxis, XYChart}

object SimulationLineChart  {

  def apply(series: Map[String, Seq[(Int, Int)]]): LineChart[Number, Number] = {
    val xAxis = NumberAxis()
    xAxis.label = "Days since epidemia has begun"
    val yAxis = NumberAxis()
    val chart = LineChart(xAxis, yAxis)

    val data = ObservableBuffer(Seq(
      (1, 23),
      (2, 14),
      (3, 15),
      (4, 24),
      (5, 34),
      (6, 36),
      (7, 22),
      (8, 45),
      (9, 43),
      (10, 17),
      (11, 29),
      (12, 25)
    ) map {case (x, y) => XYChart.Data[Number, Number](x, y)})

    val series = XYChart.Series[Number, Number]("test", data)
    chart.lookup(".default-color0.chart-symbol")
    chart.getData.add(series)
    chart
  }






}
