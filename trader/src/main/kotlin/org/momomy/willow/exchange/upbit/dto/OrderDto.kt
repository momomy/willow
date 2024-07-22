package org.momomy.willow.exchange.upbit.dto

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy::class)
data class OrderDto(
    val uuid: String = "",
    /**
     * 주문종류
     */
    @JsonAlias("side")
    val sideCode: String = "",
    /**
     * 주문방식
     */
    val ordType: String = "",
    /**
     * 주문당시화폐가격
     */
    val price: BigDecimal? = null,
    /**
     * 주문상태
     */
    @JsonAlias("state")
    val stateCode: String = "",
    /**
     * 마켓유일키
     */
    val market: Market = "",
    /**
     * 주문생성시간
     */
    val createdAt: String = "",
    /**
     * 주문량
     */
    val volume: BigDecimal? = null,
    /**
     * 체결 후 남은량
     */
    val remainingVolume: BigDecimal? = null,
    /**
     * 수수료로 예약된 비용
     */
    val reservedFee: BigDecimal? = null,
    /**
     * 남은 수수료
     */
    val remainingFee: BigDecimal? = null,
    /**
     * 사용된 수수료
     */
    var paidFee: BigDecimal? = null,
    /**
     * 거래에 사용중인 비용
     */
    val locked: BigDecimal? = null,
    /**
     * 체결된 양
     */
    val executedVolume: BigDecimal? = null,
    /**
     * 해당 주문에 걸린 체결 수
     */
    val tradesCount: Int? = null,

    ) {

    @get:JsonIgnore
    val safePrice: BigDecimal
        get() = paidFee!!.divide(BigDecimal.valueOf(0.0005), 4, RoundingMode.HALF_UP)
            .divide(executedVolume!!, 4, RoundingMode.HALF_UP)


    @get:JsonIgnore
    val state: State
        get() = State.find(this.stateCode)

    @get:JsonIgnore
    val side: OrderRequestDto.SideType
        get() = OrderRequestDto.SideType.find(this.sideCode)

    /**
     * KST
     */
    @get:JsonIgnore
    val createdAtLocalDateTime: LocalDateTime
        get() = ZonedDateTime.parse(createdAt).toLocalDateTime()

    /**
     * UTC
     */
    @get:JsonIgnore
    val utcCreatedAt: LocalDateTime
        get() = ZonedDateTime.parse(createdAt).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()


    enum class State(
        val code: String
    ) {
        WAIT("wait"),
        WATCH("watch"),
        DONE("done"),
        CANCEL("cancel");

        companion object {
            fun find(code: String): State {
                return values().first { it.code == code }

            }
        }

    }
}
