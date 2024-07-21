package org.momomy.willow.exchange.upbit.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import java.time.LocalTime

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy::class)
data class TickerDto(
    // 마켓명
    val market: Market,
    // 최근 거래 일자(UTC)
    @DateTimeFormat(pattern = "yyyyMMdd")
    @JsonFormat(pattern = "yyyyMMdd")
    val tradeDate: LocalDate,
    // 최근 거래 시각(UTC)
    @DateTimeFormat(pattern = "HHmmss")
    @JsonFormat(pattern = "HHmmss")
    val tradeTime: LocalTime,
    // 최근 거래 일자(KST)
    @DateTimeFormat(pattern = "yyyyMMdd")
    @JsonFormat(pattern = "yyyyMMdd")
    val tradeDateKst: LocalDate,
    // 최근 거래 시각(KST)
    @DateTimeFormat(pattern = "HHmmss")
    @JsonFormat(pattern = "HHmmss")
    val tradeTimeKst: LocalTime,
    // 최근 거래 일시(UTC)
    val tradeTimestamp: Long,
    // 시가
    val openingPrice: Double,
    // 고가
    val highPrice: Double,
    // 저가
    val lowPrice: Double,
    // 종가(현재가)
    val tradePrice: Double,
    // 전일 종가(UTC 0시 기준)
    val prevClosingPrice: Double,
    // EVEN : 보합, RISE : 상승, FALL : 하락
    val change: String,
    // 변화액의 절대값
    val changePrice: Double,
    // 변화율의 절대값
    val changeRate: Double,
    // 부호가 있는 변화액
    val signedChangePrice: Double,
    // 부호가 있는 변화율
    val signedChangeRate: Double,
    // 가장 최근 거래량
    val tradeVolume: Double,
    // 누적 거래대금(UTC 0시 기준)
    val accTradePrice: Double,
//    // 24시간 누적 거래대금
//    val accTradePrice24h: Double,
//    // 누적 거래량(UTC 0시 기준)
//    val accTradeVolume: Double,
//    // 24시간 누적 거래량
//    val accTradeVolume24h: Double,
//    // 52주 신고가
//    val highest52WeekPrice: Double,
//    // 52주 신고가 달성일 포맷: yyyy-MM-dd
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    val highest52weekDate: LocalDate,
//    // 52주 신저가
//    val lowest52WeekPrice: Double,
//    // 52주 신저가 달성일 포맷: yyyy-MM-dd
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    val lowest52WeekDate: String,
    // 타임스탬프
    val timestamp: Long
)
