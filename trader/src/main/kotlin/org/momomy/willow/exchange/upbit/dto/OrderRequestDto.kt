package org.momomy.willow.exchange.upbit.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.math.BigDecimal

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy::class)
data class OrderRequestDto (
    /**
     * 마켓 ID
     */
    val market: Market,
    /**
     * 주문종류
     */
    val side: SideType,
    /**
     * 주문량 (지정가, 시장가 매도 시 필수)
     */
    val volume: BigDecimal? = null,
    /**
     * 주문 가격. (지정가, 시장가 매수 시 필수)
     */
    val price: BigDecimal? = null,
    /**
     * 주문타입
     */
    val ordType: OrderType,
) {
    companion object {
        fun bidMarketPrice(market: Market, price: BigDecimal): OrderRequestDto {
            return OrderRequestDto(
                market = market,
                side = SideType.BID,
                price = price,
                ordType = OrderType.PRICE
            )
        }

        fun askMarketPrice(market: Market, volume: BigDecimal): OrderRequestDto {
            return OrderRequestDto(
                market = market,
                side = SideType.ASK,
                volume = volume,
                ordType = OrderType.MARKET
            )
        }

        fun bid(market: Market, price: BigDecimal, volume: BigDecimal): OrderRequestDto {
            return OrderRequestDto(
                market = market,
                side = SideType.BID,
                price = price,
                volume = volume,
                ordType = OrderType.LIMIT
            )
        }

        fun ask(market: Market, price: BigDecimal, volume: BigDecimal): OrderRequestDto {
            return OrderRequestDto(
                market = market,
                side = SideType.ASK,
                price = price,
                volume = volume,
                ordType = OrderType.LIMIT
            )
        }
    }

    enum class SideType(val code: String) {
        /**
         * 매수
         */
        BID("bid"),

        /**
         * 매도
         */
        ASK("ask");

        companion object {
            fun find(code: String): SideType {
                return entries.first { it.code == code }
            }
        }
    }

    enum class OrderType(val code: String) {
        /**
         * 지정가주문
         */
        LIMIT("limit"),

        /**
         * 시장가 주문(매수)
         */
        PRICE("price"),

        /**
         * 시장가 주문(매도)
         */
        MARKET("market");
    }

}
