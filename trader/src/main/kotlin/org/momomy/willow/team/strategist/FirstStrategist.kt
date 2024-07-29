package org.momomy.willow.team.strategist

import org.momomy.willow.exchange.upbit.dto.Market
import org.momomy.willow.team.analyst.Analyst
import org.momomy.willow.team.collector.Collector

class FirstStrategist : Strategist {
    override fun formulate(
        market: Market,
        data: Collector.Data,
        report: Analyst.Report
    ): Strategist.Strategy {
        return Strategist.Strategy.hold()
            .also { println("# do formulate.") }
    }
}