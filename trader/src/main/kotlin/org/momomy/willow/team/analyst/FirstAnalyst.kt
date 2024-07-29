package org.momomy.willow.team.analyst

import org.momomy.willow.team.collector.Collector

class FirstAnalyst : Analyst {
    override fun analyse(data: Collector.Data): Analyst.Report {

        return Analyst.Report(listOf(Analyst.Metric(1.0F), Analyst.Metric(2.0F)))
            .also { println("# do analyse") }
    }
}