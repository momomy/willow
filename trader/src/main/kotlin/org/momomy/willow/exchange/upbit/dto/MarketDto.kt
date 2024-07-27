package org.momomy.willow.exchange.upbit.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy::class)
data class MarketDto(
    val market: Market,
    val koreanName: String,
    val englishName: String,
    val marketEvent: Event
) {
    val marketType: MarketType
        get() = MarketType.find(market)

    enum class MarketType(val prefix: String) {
        UNKNOWN(""),
        KRW("KRW"),
        BTC("BTC"),
        USDT("USDT");

        companion object {
            private val MAP = entries.associateBy { it.prefix }

            fun find(market: String): MarketType {
                return market.split('-')[0]
                    .let { MAP[it] ?: UNKNOWN }
            }
        }
    }

    val warning: Boolean
        get() = marketEvent.warning


    data class Event(
        /**
         * 유의종목 지정 여부
         */
        val warning: Boolean,
        /**
         * 주의종목 지정 여부
         */
        val caution: Map<String, Boolean>
    )
}
