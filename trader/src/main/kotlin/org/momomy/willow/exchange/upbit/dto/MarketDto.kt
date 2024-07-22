package org.momomy.willow.exchange.upbit.dto

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy::class)
data class MarketDto(
    val market: Market,
    val koreanName: String,
    val englishName: String,
    val marketWarning: MarketWarningType
) {
    val marketType: MarketType
        get() = MarketType.find(market)

    enum class MarketType(val prefix: String) {
        UNKNOWN(""),
        KRW("KRW"),
        BTC("BTC"),
        USDT("USDT");

        companion object {
            private val MAP = MarketType.values().associateBy { it.prefix }

            fun find(market: String): MarketType {
                return market.split('-')[0]
                    .let { MAP[it] ?: UNKNOWN }
            }
        }
    }

    val warning: Boolean
        get() = marketWarning != MarketWarningType.NONE

    enum class MarketWarningType {

        // 해당없음
        @JsonEnumDefaultValue
        NONE,

        // 투자유의
        CAUTION
    }
}
