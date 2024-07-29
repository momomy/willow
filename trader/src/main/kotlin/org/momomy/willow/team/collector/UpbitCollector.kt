package org.momomy.willow.team.collector

import org.momomy.willow.exchange.upbit.UpbitClient
import org.momomy.willow.exchange.upbit.dto.Market

class UpbitCollector : Collector {
    override fun collect(market: Market): Collector.Data {

        return Collector.Data(
            candles = UpbitClient.getDaysCandles(market = market).collectList().block()!!
        ).also { println("# do collect market $market") }
    }
}