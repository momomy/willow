package org.momomy.willow.agent

import org.momomy.willow.exchange.upbit.dto.Market
import org.momomy.willow.team.Team

class InvestmentAgent(
    val team: Team
) {
    fun invest(market: Market) {
        val data = team.collector.collect(
            market = market
        )
        val report = team.analyst.analyse(
            data = data
        )
        val strategy = team.strategist.formulate(
            market = market,
            data = data,
            report = report
        )
        team.trader.trade(
            market = market,
            strategy = strategy
        )
        team.recorder.recode(
            market = market,
            data = data,
            strategy = strategy,
            report = report
        )
    }
}