package org.momomy.willow.exchange.upbit.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.LocalDateTime

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy::class)
interface CandleDto {
    /**
     * Market
     */
    val market: Market

    /**
     * 종가
     */
    val tradePrice: Double

    /**
     * 고가
     */
    val highPrice: Double

    /**
     * 저가
     */
    val lowPrice: Double

    /**
     * 시간(UTC)
     */
    val candleDateTimeUtc: LocalDateTime

    /**
     * 시간(KST)
     */
    val candleDateTimeKst: LocalDateTime

    /**
     * 거래량
     */
    val candleAccTradeVolume: Double
}
