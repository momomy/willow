package org.momomy.willow.exchange.upbit.dto

typealias Market = String
typealias Currency = String

fun Market.currency(): Currency = this.split("-")[1]
fun Currency.toKrw(): Market = "KRW-$this"

