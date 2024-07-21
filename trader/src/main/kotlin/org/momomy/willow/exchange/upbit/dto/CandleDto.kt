package org.momomy.willow.exchange.upbit.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.momomy.willow.exchange.upbit.unit.TradingPriceUnitOptimizer
import java.math.BigDecimal
import java.math.RoundingMode
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

    /**
     * 특정 호가 단위로 조정 된 가격
     */
    fun getAdjustTradePrice(tradePriceUnitRate: Double): Double {
        val scale = TradingPriceUnitOptimizer.scale(tradePrice)
        val tradingPriceUnit =
            (BigDecimal.valueOf(tradePrice) * BigDecimal.valueOf(tradePriceUnitRate))
                .setScale(scale, RoundingMode.HALF_UP)

        return BigDecimal.valueOf(tradePrice)
            .setScale(scale, RoundingMode.HALF_UP)
            .divide(tradingPriceUnit, 0, RoundingMode.HALF_UP)
            .multiply(tradingPriceUnit)
            .toDouble()
    }

}
