package ui

import scalafx.beans.property.{DoubleProperty, IntegerProperty}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label, TextField, TextFormatter}
import scalafx.scene.layout.{BorderPane, Pane, StackPane, VBox}
import scalafx.scene.paint.Color._
import scalafx.scene.text.Text

class SimulationBoardScene() extends Scene {
  fill = Black
  stylesheets.add("line_chart.css")

  val population: IntegerProperty = IntegerProperty(0)
  val initSick: IntegerProperty = IntegerProperty(0)
  val incidenceRate: DoubleProperty = DoubleProperty(0)
  val mortality: DoubleProperty = DoubleProperty(0)
  val diseaseDuration: IntegerProperty = IntegerProperty(0)

  val integerFilter: (TextFormatter.Change) => TextFormatter.Change = change =>  {
    if (change.controlNewText.matches("[0-9]*")) {
      change
    } else {
      null
    }
  }

  val doubleFilter: (TextFormatter.Change) => TextFormatter.Change = change =>  {
    if (change.controlNewText.matches("^[0-9]+\\.[0-9]*$|^([0-9]*)$")) {
      change
    } else {
      null
    }
  }

  val populationField = new TextField {
    textFormatter = new TextFormatter(integerFilter)
    text.onChange((_, _, newValue) => {
      population.value = util.Try(newValue.toInt).getOrElse(0)
    })
    promptText = "Population"
    maxWidth = 200
  }

  val initSickField = new TextField {
    textFormatter = new TextFormatter(integerFilter)
    text.onChange((_, _, newValue) => {
      initSick.value = util.Try(newValue.toInt).getOrElse(0)
    })
    promptText = "Initial infections"
    maxWidth = 200
  }

  val incidenceRateField = new TextField {
    textFormatter = new TextFormatter(doubleFilter)
    text.onChange((_, _, newValue) => {
      incidenceRate.value = util.Try(newValue.toDouble).getOrElse(0.0)
    })
    promptText = "Incidence rate"
    maxWidth = 200
  }

  val mortalityField = new TextField {
    textFormatter = new TextFormatter(doubleFilter)
    text.onChange((_, _, newValue) => {
      mortality.value = util.Try(newValue.toDouble).getOrElse(0.0)
    })
    promptText = "Mortality rate"
    maxWidth = 200
  }

  val diseaseDurationField = new TextField {
    textFormatter = new TextFormatter(integerFilter)
    text.onChange((_, _, newValue) => {
      diseaseDuration.value = util.Try(newValue.toInt).getOrElse(0)
    })
    promptText = "Disease duration"
    maxWidth = 200
  }


  val confirmButton = new Button("Start simulation") {
    onMouseClicked = (_) => {
      startSimulation()
    }
  }

  def startSimulation(): Unit = {
    content = new SimulationPane(
      population = population(),
      initSick = initSick(),
      incidenceRate = incidenceRate(),
      mortality = mortality(),
      diseaseDuration = diseaseDuration(),
    )
  }

  val vBox = new VBox {
    spacing = 15
  }

  vBox.children.addAll(
    new Text {
      text = "Population: "
      fill = White
    },
    populationField,
    new Text {
      text = "Initial infections: "
      fill = White
    },
    initSickField,
    new Text {
      text = "Incidence rate: "
      fill = White
    },
    incidenceRateField,
    new Text {
      text = "Mortality rate: "
      fill = White
    },
    mortalityField,
    new Text {
      text = "Disease duration: "
      fill = White
    },
    diseaseDurationField,
    confirmButton
  )
  vBox.setAlignment(Pos.Center)
  vBox.setMaxHeight(Double.MaxValue)
  vBox.setMaxWidth(Double.MaxValue)

  vBox.prefHeight <== height
  vBox.prefWidth <== width
  content = vBox
}
