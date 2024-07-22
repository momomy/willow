package org.momomy.willow.exchange.upbit.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import java.time.LocalDateTime

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy::class)
data class MonthCandleDto(
    // 마켓명
    override val market: Market,
    // 캔들 기준 시각(UTC 기준)
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
    override val candleDateTimeUtc: LocalDateTime,
    // 캔들 기준 시각(KST 기준)
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
    override val candleDateTimeKst: LocalDateTime,
    // 시가
    val openingPrice: Double,
    // 고가
    override val highPrice: Double,
    // 저가
    override val lowPrice: Double,
    // 종가
    override val tradePrice: Double,
    // 해당 캔들에서 마지막 틱이 저장된 시각
    val timestamp: Long,
    // 누적거래금액
    val candleAccTradePrice: Double,
    // 누적거래량
    override val candleAccTradeVolume: Double,
    // 캔들 기간의 가장 첫 날
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    val firstDayOfPeriod: LocalDate
) : CandleDto
