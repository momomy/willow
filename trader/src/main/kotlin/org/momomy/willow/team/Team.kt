package org.momomy.willow.team

import org.momomy.willow.team.analyst.Analyst
import org.momomy.willow.team.collector.Collector
import org.momomy.willow.team.recoder.Recorder
import org.momomy.willow.team.strategist.Strategist
import org.momomy.willow.team.trader.Trader

interface Team {
    val collector: Collector
    val analyst: Analyst
    val strategist: Strategist
    val trader: Trader
    val recorder: Recorder
}