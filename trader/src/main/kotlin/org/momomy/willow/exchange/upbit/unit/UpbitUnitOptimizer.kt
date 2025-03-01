package org.momomy.willow.exchange.upbit.unit

import java.math.BigDecimal
import java.math.RoundingMode

object UpbitUnitOptimizer {
    fun unit(price: Double): Double {
        return when {
            2_000_000.0 <= price -> 1_000.0
            price in 1_000_000.0..2_000_000.0 -> 500.0
            price in 500_000.0..1_000_000.0 -> 100.0
            price in 100_000.0..500_000.0 -> 50.0
            price in 10_000.0..100_000.0 -> 10.0
            price in 1_000.0..10_000.0 -> 1.0
            price in 100.0..1_000.0 -> 0.1
            price in 10.0..100.0 -> 0.01
            price in 1.0..10.0 -> 0.001
            price in 0.1..1.0 -> 0.0001
            price in 0.01..0.1 -> 0.00001
            price in 0.001..0.01 -> 0.000001
            price in 0.0001..0.001 -> 0.0000001
            else -> 0.00000001
        }
    }

    fun scale(price: Double): Int {
        return when {
            2_000_000.0 <= price -> 0
            price in 1_000_000.0..2_000_000.0 -> 0
            price in 500_000.0..1_000_000.0 -> 0
            price in 100_000.0..500_000.0 -> 0
            price in 10_000.0..100_000.0 -> 0
            price in 1_000.0..10_000.0 -> 0
            price in 100.0..1_000.0 -> 1
            price in 10.0..100.0 -> 2
            price in 1.0..10.0 -> 3
            price in 0.1..1.0 -> 4
            price in 0.01..0.1 -> 5
            price in 0.001..0.01 -> 6
            price in 0.0001..0.001 -> 7
            else -> 8
        }
    }

    /**
     * 가격 기준으로 매도 가격 반환
     */
    fun getAskPrice(price: Double): Double {
        val unit = BigDecimal.valueOf(unit(price))
        return BigDecimal.valueOf(price).setScale(unit.scale(), RoundingMode.DOWN)
            .divide(unit, 0, RoundingMode.DOWN)
            .multiply(unit)
            .toDouble()
    }


    /**
     * 가격 기준으로 매수 가격 반환
     */
    fun getBidPrice(price: Double): Double {
        val unit = BigDecimal.valueOf(unit(price))
        return BigDecimal.valueOf(price).setScale(unit.scale(), RoundingMode.UP)
            .divide(unit, 0, RoundingMode.UP)
            .multiply(unit)
            .toDouble()
    }
}

private fun BigDecimal.toScale(): BigDecimal =
    this.setScale(UpbitUnitOptimizer.scale(this.toDouble()), RoundingMode.HALF_UP)

fun BigDecimal.bidPrice(): BigDecimal =
    BigDecimal.valueOf(UpbitUnitOptimizer.getBidPrice(this.toDouble())).toScale()

fun BigDecimal.askPrice(): BigDecimal =
    BigDecimal.valueOf(UpbitUnitOptimizer.getAskPrice(this.toDouble())).toScale()

fun BigDecimal.volume(): BigDecimal = this.setScale(8, RoundingMode.DOWN)