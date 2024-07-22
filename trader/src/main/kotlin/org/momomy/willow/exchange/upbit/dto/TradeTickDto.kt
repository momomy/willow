package org.momomy.willow.exchange.upbit.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import java.time.LocalTime

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy::class)
data class TradeTickDto(
    // 마켓명
    val market: Market,
    // 체결 일자(UTC 기준)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    val tradeDateUtc: LocalDate,
    // 체결 시각(UTC 기준)
    @DateTimeFormat(pattern = "HH:mm:ss")
    val tradeTimeUtc: LocalTime,
    // 체결 타임스탬프
    val timestamp: Long,
    // 체결 가격
    val tradePrice: Double,
    // 체결량
    val tradeVolume: Double,
    // 전일 종가(UTC 0시 기준)
    val prevClosingPrice: Double,
    // 변화량
    val changePrice: Double,
    // 매도/매수
    val askBid: String,
    // 체결 번호(Unique)
    val sequentialId: Long
)
