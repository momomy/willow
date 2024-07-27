package org.momomy.willow.web.controller

import org.momomy.willow.exchange.upbit.unit.bidPrice
import org.momomy.willow.exchange.upbit.unit.volume
import org.hibernate.validator.constraints.Range
import org.momomy.willow.exchange.upbit.UpbitClient
import org.momomy.willow.exchange.upbit.dto.*
import org.momomy.willow.exchange.upbit.unit.askPrice
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.time.LocalDateTime

@RestController
@RequestMapping("/upbit")
class UpbitController {

    @GetMapping("/markets")
    fun getMarkets(): Flux<MarketDto> {
        return UpbitClient.getMarkets()
    }


    @GetMapping("/{market}/candle/minutes/{unit}")
    fun getMinuteCandles(
        @PathVariable market: Market,
        @PathVariable unit: MinuteCandleDto.UNIT,
        @RequestParam(required = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        to: LocalDateTime?,
        @RequestParam(required = false, defaultValue = "200")
        @Range(min = 1, max = 200)
        count: Int

    ): Flux<MinuteCandleDto> {
        return UpbitClient.getMinutesCandles(
            unit = unit,
            market = market,
            count = count,
            to = to
        )
    }

    @GetMapping("/{market}/candle/days")
    fun getDaysCandles(
        @PathVariable market: Market,
        @RequestParam(required = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        to: LocalDateTime?,
        @RequestParam(required = false, defaultValue = "200")
        @Range(min = 1, max = 200)
        count: Int

    ): Flux<DayCandleDto> {
        return UpbitClient.getDaysCandles(
            market = market,
            count = count,
            to = to
        )
    }

    @GetMapping("/{market}/candle/weeks")
    fun getWeeksCandles(
        @PathVariable market: Market,
        @RequestParam(required = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        to: LocalDateTime?,
        @RequestParam(required = false, defaultValue = "200")
        @Range(min = 1, max = 200)
        count: Int

    ): Flux<WeekCandleDto> {
        return UpbitClient.getWeeksCandles(
            market = market,
            count = count,
            to = to
        )
    }

    @GetMapping("/{market}/candle/months")
    fun getMonthsCandles(
        @PathVariable market: Market,
        @RequestParam(required = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        to: LocalDateTime?,
        @RequestParam(required = false, defaultValue = "200")
        @Range(min = 1, max = 200)
        count: Int

    ): Flux<MonthCandleDto> {
        return UpbitClient.getMonthsCandles(
            market = market,
            count = count,
            to = to
        )
    }

    @GetMapping("/{market}/trades/ticks")
    fun getTradesTicks(
        @PathVariable market: Market,
        @RequestParam(required = false, defaultValue = "200")
        @Range(min = 1, max = 200)
        count: Int

    ): Flux<TradeTickDto> {
        return UpbitClient.getTradesTicks(
            market = market,
            count = count
        )
    }

    @GetMapping("/{markets}/ticker")
    fun getTradesTicker(
        @PathVariable markets: Set<Market>
    ): Flux<TickerDto> {
        return UpbitClient.getTicker(
            markets = markets
        )
    }

    @GetMapping("/accounts")
    fun getAccounts(): Flux<AccountDto> {
        return UpbitClient.getAccounts()
    }

    @GetMapping("/orders/{market}")
    fun getOrders(
        @PathVariable market: Market,
        @RequestParam state: OrderDto.State,
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = false, defaultValue = "100")
        @Range(min = 1, max = 100)
        limit: Int
    ): Flux<OrderDto> {
        return UpbitClient.getOrderList(
            market = market,
            state = state,
            page = page,
            limit = limit
        )
    }

    @PostMapping("/order/{market}/bid")
    fun bid(
        @PathVariable market: Market,
        @RequestParam price: BigDecimal,
        @RequestParam volume: BigDecimal
    ): Mono<OrderDto> {
        return UpbitClient.bid(
            market = market,
            price = price.bidPrice(),
            volume = volume.volume()
        )
    }

    @PostMapping("/order/{market}/bid/market")
    fun bidMarketPrice(
        @PathVariable market: Market,
        @RequestParam price: BigDecimal,
    ): Mono<OrderDto> {
        return UpbitClient.bidMarketPrice(
            market = market,
            price = price.bidPrice()
        )
    }

    @PostMapping("/order/{market}/ask")
    fun ask(
        @PathVariable market: Market,
        @RequestParam price: BigDecimal,
        @RequestParam volume: BigDecimal
    ): Mono<OrderDto> {
        return UpbitClient.ask(
            market = market,
            price = price.askPrice(),
            volume = volume.volume()
        )
    }

    @PostMapping("/order/{market}/ask/market")
    fun askMarketPrice(
        @PathVariable market: Market,
        @RequestParam volume: BigDecimal,
    ): Mono<OrderDto> {
        return UpbitClient.askMarketPrice(
            market = market,
            volume = volume.volume()
        )
    }

    @DeleteMapping("/order/{uuid}")
    fun cancel(
        @PathVariable uuid: String
    ): Mono<OrderDto> {
        return UpbitClient.cancel(
            uuid = uuid
        )
    }

    @GetMapping("/orders/{market}/open")
    fun getOpenOrders(
        @PathVariable market: Market,
        @RequestParam(required = false, defaultValue = "1") state: OrderDto.State,
        @RequestParam(required = false, defaultValue = "1") page: Int,
    ): Flux<OrderDto> {
        return UpbitClient.getOpenOrderList(
            market = market,
            state = state,
            page = page
        )
    }
}