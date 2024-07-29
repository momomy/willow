package org.momomy.willow.team.collector

import org.momomy.willow.exchange.upbit.dto.CandleDto
import org.momomy.willow.exchange.upbit.dto.Market

fun interface Collector {
    fun collect(market: Market): Data

    data class Data(
        val candles: List<CandleDto>
    )
}