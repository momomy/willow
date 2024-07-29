package org.momomy.willow.team.analyst

import org.momomy.willow.team.collector.Collector

fun interface Analyst {
    fun analyse(data: Collector.Data): Report

    data class Report(
        val metricList: List<Metric>
    )

    data class Metric(
        val metric: Float
    )
}