package org.momomy.willow.exchange.upbit

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.ObjectMapper
import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import org.momomy.willow.exchange.upbit.dto.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider
import reactor.util.retry.Retry
import java.math.BigDecimal
import java.math.BigInteger
import java.security.MessageDigest
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.concurrent.TimeUnit


object UpbitClient {

    val CONNECT_TIMEOUT_MILLIS = Duration.ofSeconds(3).toMillis().toInt()
    val READ_TIMEOUT_MILLIS = Duration.ofSeconds(10).toMillis()
    const val SERVER_URL = "https://api.upbit.com"
    const val RETRY_COUNT = 10_000L
    const val MAX_CANDLE_COUNT = 200

    private val log: Logger = LoggerFactory.getLogger(UpbitClient::class.java)

    private val webClient: WebClient = WebClient.builder()
        .clientConnector(ReactorClientHttpConnector(
            HttpClient.create(
                ConnectionProvider.builder("http-pool")
                    .maxConnections(100)
                    .pendingAcquireTimeout(Duration.ofMillis(0))
                    .pendingAcquireMaxCount(-1)
                    .maxIdleTime(Duration.ofMillis(2_000L))
                    .build()
            )
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, CONNECT_TIMEOUT_MILLIS)
                .doOnConnected {
                    it.addHandlerLast(ReadTimeoutHandler(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS))
                }
        ))
        .exchangeStrategies(
            ExchangeStrategies.builder()
                .codecs { it.defaultCodecs().maxInMemorySize(1024 * 1024 * 1) }
                .build())
        .baseUrl(SERVER_URL)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build()

    /**
     * 마켓조회
     * https://docs.upbit.com/reference/%EB%A7%88%EC%BC%93-%EC%BD%94%EB%93%9C-%EC%A1%B0%ED%9A%8C
     */
    fun getMarkets(): Flux<MarketDto> {
        return webClient.get()
            .uri("/v1/market/all?isDetails=true")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(MarketDto::class.java)
            .retryWhen(Retry.fixedDelay(RETRY_COUNT, Duration.ofMillis(100)))
    }

    /**
     * 분 캔들 조회
     * https://docs.upbit.com/reference/%EB%B6%84minute-%EC%BA%94%EB%93%A4-1
     */
    fun getMinutesCandles(
        unit: MinuteCandleDto.UNIT,
        market: Market,
        count: Int = MAX_CANDLE_COUNT,
        to: LocalDateTime?
    ): Flux<MinuteCandleDto> {
        return webClient.get()
            .uri(
                "/v1/candles/minutes/{0}?market={1}&count={2}&to={3}",
                unit.minute,
                market,
                count,
                to?.toParam()
            )
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(MinuteCandleDto::class.java)
            .retryWhen(Retry.fixedDelay(RETRY_COUNT, Duration.ofMillis(100)))
    }

    /**
     * 일 캔들 조회
     * https://docs.upbit.com/reference/%EC%9D%BCday-%EC%BA%94%EB%93%A4-1
     */
    fun getDaysCandles(
        market: Market,
        count: Int = MAX_CANDLE_COUNT,
        to: LocalDateTime?
    ): Flux<DayCandleDto> {
        return webClient.get()
            .uri(
                "/v1/candles/days?market={0}&count={1}&to={2}",
                market,
                count,
                to?.toParam()
            )
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(DayCandleDto::class.java)
            .retryWhen(Retry.fixedDelay(RETRY_COUNT, Duration.ofMillis(100)))
    }

    /**
     * 주 캔들 조회
     * https://docs.upbit.com/reference/%EC%A3%BCweek-%EC%BA%94%EB%93%A4-1
     */
    fun getWeeksCandles(
        market: Market,
        count: Int = MAX_CANDLE_COUNT,
        to: LocalDateTime?
    ): Flux<WeekCandleDto> {
        return webClient.get()
            .uri(
                "/v1/candles/weeks?market={0}&count={1}&to={2}",
                market,
                count,
                to?.toParam()
            )
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(WeekCandleDto::class.java)
            .retryWhen(Retry.fixedDelay(RETRY_COUNT, Duration.ofMillis(100)))
    }

    /**
     * 월 캔들 조회
     * https://docs.upbit.com/reference/%EC%A3%BCweek-%EC%BA%94%EB%93%A4-1
     */
    fun getMonthsCandles(
        market: Market,
        count: Int = MAX_CANDLE_COUNT,
        to: LocalDateTime?
    ): Flux<MonthCandleDto> {
        return webClient.get()
            .uri(
                "/v1/candles/months?market={0}&count={1}",
                market,
                count,
                to?.toParam()
            )
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(MonthCandleDto::class.java)
            .retryWhen(Retry.fixedDelay(RETRY_COUNT, Duration.ofMillis(100)))
    }

    /**
     * 최근 채결 내역
     * https://docs.upbit.com/reference/%EC%B5%9C%EA%B7%BC-%EC%B2%B4%EA%B2%B0-%EB%82%B4%EC%97%AD
     */
    fun getTradesTicks(
        market: Market,
        count: Int
    ): Flux<TradeTickDto> {
        return webClient.get()
            .uri("/v1/trades/ticks?market={0}&count={1}", market, count)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(TradeTickDto::class.java)
            .retryWhen(Retry.fixedDelay(RETRY_COUNT, Duration.ofMillis(100)))
    }

    /**
     * 현재가 정보
     * https://docs.upbit.com/reference/ticker%ED%98%84%EC%9E%AC%EA%B0%80-%EC%A0%95%EB%B3%B4
     */
    fun getTicker(markets: Set<Market>): Flux<TickerDto> {
        return webClient.get()
            .uri("/v1/ticker?markets={0}", markets.joinTo(StringBuilder()).toString())
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(TickerDto::class.java)
            .retryWhen(Retry.fixedDelay(RETRY_COUNT, Duration.ofMillis(100)))
    }

    fun getAccounts(): Flux<AccountDto> {

        val authenticationToken = generateAuthenticationToken()

        return webClient.get()
            .uri("/v1/accounts")
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", authenticationToken)
            .retrieve()
            .bodyToFlux(AccountDto::class.java)
            .retryWhen(Retry.fixedDelay(RETRY_COUNT, Duration.ofMillis(100)))
    }

    /**
     * 시장가 매수
     */
    fun bidMarketPrice(market: Market, price: BigDecimal): Mono<OrderDto> {
        return order(OrderRequestDto.bidMarketPrice(market, price))
    }

    /**
     * 시장가 매도
     */
    fun askMarketPrice(market: Market, volume: BigDecimal): Mono<OrderDto> {
        return order(OrderRequestDto.askMarketPrice(market, volume))
    }

    /**
     * 매수
     */
    fun bid(market: Market, price: BigDecimal, volume: BigDecimal): Mono<OrderDto> {
        return order(
            OrderRequestDto.bid(
                market = market,
                price = price,
                volume = volume
            )
        )
    }

    /**
     * 매도
     */
    fun ask(market: Market, price: BigDecimal, volume: BigDecimal): Mono<OrderDto> {
        return order(
            OrderRequestDto.ask(
                market = market,
                price = price,
                volume = volume
            )
        )
    }

    private fun order(request: OrderRequestDto): Mono<OrderDto> {

        val params: HashMap<String, String?> = HashMap()
        params["market"] = request.market
        params["side"] = request.side.code
        when (request.ordType) {
            OrderRequestDto.OrderType.LIMIT -> {
                params["volume"] = request.volume.toString()
                params["price"] = request.price.toString()
            }

            OrderRequestDto.OrderType.PRICE -> {
                check(request.side == OrderRequestDto.SideType.BID)
                params["price"] = request.price.toString()
                params["volume"] = null
            }

            OrderRequestDto.OrderType.MARKET -> {
                check(request.side == OrderRequestDto.SideType.ASK)
                params["price"] = null
                params["volume"] = request.volume.toString()
            }
        }
        params["ord_type"] = request.ordType.code


        val queryString: String = params.map { it.key + "=" + (it.value ?: "") }
            .joinTo(StringBuilder(), "&").toString()

        val authenticationToken = generateAuthenticationToken(queryString)

        val body = ObjectMapper().writeValueAsString(params)
        return webClient.post()
            .uri("/v1/orders")
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", authenticationToken)
            .bodyValue(body)
            .retrieve()
            .onStatus({
                !it.is2xxSuccessful
            }, { response ->
                response
                    .bodyToMono(String::class.java)
                    .map {
                        logResponseError(it)
                        RuntimeException(it)
                    }
            })
            .bodyToMono(OrderDto::class.java)
            .doOnError {
                log.error("## failed", it)
            }
    }

    fun cancel(uuid: String): Mono<OrderDto> {

        val queryString = "uuid=$uuid"
        val authenticationToken = generateAuthenticationToken(queryString)

        return webClient.delete()
            .uri("/v1/order?$queryString")
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", authenticationToken)
            .retrieve()
            .onStatus({
                !it.is2xxSuccessful
            }, { response ->
                response
                    .bodyToMono(String::class.java)
                    .map {
                        logResponseError(it)
                        RuntimeException(it)
                    }
            })
            .bodyToMono(OrderDto::class.java)
            .doOnError {
                log.error("## failed", it)
            }
    }

    fun getOrderList(
        market: Market,
        state: OrderDto.State,
        page: Int,
        limit: Int
    ): Flux<OrderDto> {

        val queryString = "market=$market&state=${state.code}&limit=$limit&page=$page"
        val authenticationToken = generateAuthenticationToken(queryString)

        return webClient.get()
            .uri("/v1/orders?$queryString")
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", authenticationToken)
            .retrieve()
            .onStatus({
                !it.is2xxSuccessful
            }, { response ->
                response
                    .bodyToMono(String::class.java)
                    .map {
                        logResponseError(it)
                        RuntimeException(it)
                    }
            })
            .bodyToFlux(OrderDto::class.java)
            .retryWhen(Retry.fixedDelay(RETRY_COUNT, Duration.ofMillis(100)))
    }

    private fun generateAuthenticationToken(queryString: String? = null): String {
        val accessKey = System.getenv("UPBIT_OPEN_API_ACCESS_KEY")
        val secretKey = System.getenv("UPBIT_OPEN_API_SECRET_KEY")

        val nonce = UUID.randomUUID().toString()

        val algorithm = Algorithm.HMAC256(secretKey)
        val jwtToken = if (queryString == null) {
            JWT.create()
                .withClaim("access_key", accessKey)
                .withClaim("nonce", nonce)
                .sign(algorithm)
        } else {
            val md: MessageDigest = MessageDigest.getInstance("SHA-512")
            md.update(queryString.toByteArray(charset("UTF-8")))

            val queryHash = java.lang.String.format("%0128x", BigInteger(1, md.digest()))
            JWT.create()
                .withClaim("access_key", accessKey)
                .withClaim("nonce", nonce)
                .withClaim("query_hash", queryHash)
                .withClaim("query_hash_alg", "SHA512")
                .sign(algorithm)
        }

        return "Bearer $jwtToken"
    }

    private fun logResponseError(response: String) {
        log.info("## responseBody: {}", response)
    }
}

fun LocalDateTime.toParam(): String =
    this.truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_DATE_TIME)
