package org.momomy.willow.team.recoder

import org.momomy.willow.exchange.upbit.dto.Market
import org.momomy.willow.team.analyst.Analyst
import org.momomy.willow.team.collector.Collector
import org.momomy.willow.team.strategist.Strategist

class FirstRecorder : Recorder {
    override fun recode(
        market: Market,
        data: Collector.Data,
        report: Analyst.Report,
        strategy: Strategist.Strategy
    ) {
        println("# do recorde.")
    }

}