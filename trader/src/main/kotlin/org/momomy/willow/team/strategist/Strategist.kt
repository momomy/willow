package org.momomy.willow.team.strategist

import org.momomy.willow.exchange.upbit.dto.Market
import org.momomy.willow.team.analyst.Analyst
import org.momomy.willow.team.collector.Collector
import java.math.BigDecimal

fun interface Strategist {
    fun formulate(market: Market, data: Collector.Data, report: Analyst.Report): Strategy

    data class Strategy(
        val order: OrderType,
        val price: BigDecimal,
        val volume: BigDecimal,
    ) {
        companion object {
            fun hold(): Strategy {
                return Strategy(order = OrderType.HOLD, price = BigDecimal.ZERO, volume = BigDecimal.ZERO)
            }

            fun ask(price: BigDecimal, volume: BigDecimal): Strategy {
                return Strategy(order = OrderType.ASK, price = price, volume = volume)
            }

            fun bid(price: BigDecimal, volume: BigDecimal): Strategy {
                return Strategy(order = OrderType.BID, price = price, volume = volume)
            }
        }
    }

    enum class OrderType {
        /**
         * 매도
         */
        ASK,

        /**
         * 매수
         */
        BID,

        /**
         * 유지
         */
        HOLD
    }
}