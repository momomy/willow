package org.momomy.willow.exchange.upbit.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy::class)
data class MinuteCandleDto(
    // 마켓명
    override val market: Market = "",
    // 캔들 기준 시각(UTC 기준)
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
    override val candleDateTimeUtc: LocalDateTime = LocalDateTime.now(),
    // 캔들 기준 시각(KST 기준)
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
    override val candleDateTimeKst: LocalDateTime = LocalDateTime.now(),
    // 시가
    val openingPrice: Double = 0.0,
    // 고가
    override val highPrice: Double = 0.0,
    // 저가
    override val lowPrice: Double = 0.0,
    // 종가
    override val tradePrice: Double = 0.0,
    // 해당 캔들에서 마지막 틱이 저장된 시각
    val timestamp: Long = 0,
    // 누적거래금액
    val candleAccTradePrice: Double = 0.0,
    // 누적거래량
    override val candleAccTradeVolume: Double = 0.0,
    // 분단위
    val unit: Int = 0
) : CandleDto {

    enum class UNIT(val minute: Int) {
        M1(1),
        M3(3),
        M5(5),
        M15(15),
        M10(10),
        M30(30),
        M60(60),
        M240(240);
    }
}
