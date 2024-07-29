package org.momomy.willow.web.controller

import org.momomy.willow.agent.InvestmentAgent
import org.momomy.willow.exchange.upbit.dto.Market
import org.momomy.willow.team.UpbitTeam
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/investments")
class InvestmentController {

    @PostMapping("/{market}")
    suspend fun invest(
        @PathVariable market: Market
    ) {

        InvestmentAgent(
            team = UpbitTeam()
        ).invest(market)

    }
}