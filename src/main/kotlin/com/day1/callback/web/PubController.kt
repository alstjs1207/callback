package com.day1.callback.web

import com.day1.callback.service.redis.impl.RedisPublisher
import com.day1.callback.util.CommonDef
import com.day1.callback.web.dto.ImpRequestDto
import com.day1.callback.web.dto.RedisRequestDto
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import lombok.RequiredArgsConstructor
import mu.KotlinLogging
import org.springframework.web.bind.annotation.*


private val logger = KotlinLogging.logger {}

@RequiredArgsConstructor
@RestController
@RequestMapping("/callback")
class PubController(
    val redisPublisher: RedisPublisher
) {

    val om = jacksonObjectMapper()

    /**
     * 사이트에 따른 redis publish
     */
    @PostMapping("/publish/{site}")
    fun pubMessage(@RequestBody redisRequestDto: RedisRequestDto, @PathVariable site: String) {
        logger.info { "data: $redisRequestDto" }
        var jsonStr = om.writeValueAsString(redisRequestDto)
        redisPublisher.runPublish(site, channelName = CommonDef.EMPLOYEE_BUS.key, jsonStr)
    }
}