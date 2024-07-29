package org.momomy.willow.team

import org.momomy.willow.team.analyst.Analyst
import org.momomy.willow.team.analyst.FirstAnalyst
import org.momomy.willow.team.collector.Collector
import org.momomy.willow.team.collector.UpbitCollector
import org.momomy.willow.team.recoder.FirstRecorder
import org.momomy.willow.team.recoder.Recorder
import org.momomy.willow.team.strategist.FirstStrategist
import org.momomy.willow.team.strategist.Strategist
import org.momomy.willow.team.trader.Trader
import org.momomy.willow.team.trader.UpbitTrader

class UpbitTeam : Team {
    override val collector: Collector by lazy {
        UpbitCollector()
    }

    override val analyst: Analyst by lazy {
        FirstAnalyst()
    }

    override val strategist: Strategist by lazy {
        FirstStrategist()
    }

    override val trader: Trader by lazy {
        UpbitTrader()
    }

    override val recorder: Recorder by lazy {
        FirstRecorder()
    }
}