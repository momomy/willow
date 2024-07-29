package org.momomy.willow.team.trader

import org.momomy.willow.exchange.upbit.UpbitClient
import org.momomy.willow.exchange.upbit.dto.Market
import org.momomy.willow.exchange.upbit.unit.askPrice
import org.momomy.willow.exchange.upbit.unit.bidPrice
import org.momomy.willow.exchange.upbit.unit.volume
import org.momomy.willow.team.strategist.Strategist
import java.math.BigDecimal

class UpbitTrader : Trader {
    override fun trade(market: Market, strategy: Strategist.Strategy) {
        doTrade(market = market, strategy = strategy)
            .also { println("# do trade. market: $market, strategy: $strategy") }
    }

    private fun doTrade(market: Market, strategy: Strategist.Strategy) {
        val price = optimizePrice(strategy)
        val volume = strategy.volume.volume()
        val orderAmount = price * volume

        // 원화 마켓 기준으로 5,000원 이상만 거래가 가능하다.
        if (orderAmount < BigDecimal.valueOf(5_000)) return

        when (strategy.order) {
            Strategist.OrderType.ASK -> UpbitClient.ask(market, price, volume)
            Strategist.OrderType.BID -> UpbitClient.bid(market, price, volume)
            Strategist.OrderType.HOLD -> Unit
        }
    }

    private fun optimizePrice(strategy: Strategist.Strategy): BigDecimal {
        return when (strategy.order) {
            Strategist.OrderType.ASK -> strategy.price.askPrice()
            Strategist.OrderType.BID -> strategy.price.bidPrice()
            else -> strategy.price
        }
    }
}