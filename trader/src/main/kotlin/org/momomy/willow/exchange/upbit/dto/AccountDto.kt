package org.momomy.willow.exchange.upbit.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.math.BigDecimal

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy::class)
data class AccountDto(
    val currency: Currency,
    val balance: BigDecimal,
    val locked: BigDecimal,
    val avgBuyPrice: BigDecimal,
    val avgBuyPriceModified: Boolean,
    val unitCurrency: String
)
