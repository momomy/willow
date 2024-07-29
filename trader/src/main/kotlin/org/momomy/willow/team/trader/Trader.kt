package org.momomy.willow.team.trader

import org.momomy.willow.exchange.upbit.dto.Market
import org.momomy.willow.team.strategist.Strategist

fun interface Trader {
    fun trade(market: Market, strategy: Strategist.Strategy)
}