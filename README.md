<p align="center">  
  <img src="./demo.gif"/>
</p>
<h1 align="center">
  Epidemic Simulator
</h1>

>This project allows you to simulate an epidemic. You can monitor the progress of *healthy*, *infected*, *recovered* and *dead* patients every day on multiple charts and visualisations.

[![Generic badge](https://img.shields.io/badge/Build%20with-Scala-red.svg)](https://shields.io/)
[![GitHub pull-requests](https://img.shields.io/github/issues-pr/Naereen/StrapDown.js.svg)](https://github.com/MikeyZat/epidemic-simulator/pulls)

## The epidemic parameters
You can start your simulation by passing these essential parameters:
  - Population size
  - Initial number of infected people
  - Incidence rate - [read more](https://en.wikipedia.org/wiki/Incidence_(epidemiology))
  - Mortality rate
  - Disease duration - an avarage time after which the patient recovers (or unfortunately, dies)

## Tools used
The whole application is written in **Scala**, the UI code can be found [here](https://github.com/MikeyZat/epidemic-simulator/tree/master/src/main/scala/ui), while the  simulation logic is [here](https://github.com/MikeyZat/epidemic-simulator/tree/master/src/main/scala/simulation).
| Area | Tool |
| ------ | ------ |
| Visualisation | ScalaFX |
| Simulation Logic | Parallel Monte Carlo samplings |

## Motivation

It was our project for Scala Programming Labolatories at the University. We wanted to make something interesting and entertaining using the power of **Scala**.

## Authors

<table>
  <tr>
    <td align="center"><a href="https://github.com/MikeyZat"><img src="https://avatars0.githubusercontent.com/u/41756225?s=460&u=a8048220c6af35242049df4c497a8a7a759840bc&v=4" width="100px;" alt=""/><br /><sub><b>Mikołaj Zatorski</b></sub></a></td>
   <td align="center"><a href="https://github.com/Qizot"><img src="https://avatars0.githubusercontent.com/u/34857220?s=460&u=594645f4b7548bb57393509b17a031e88f04d81c&v=4" width="100px;" alt=""/><br /><sub><b>Jakub Perżyło</b></sub></a></td>
    </tr>
</table
