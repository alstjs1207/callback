package com.day1.callback.web

import com.day1.callback.aspect.ChannelsAdvice
import com.day1.callback.service.exception.ErrorCode
import com.day1.callback.service.exception.ErrorException
import com.day1.callback.service.redis.RedisPublisher
import com.day1.callback.service.redis.impl.RedisMessageDtoSubscriber
import com.day1.callback.util.CommonDef
import com.day1.callback.web.dto.ImpRequestDto
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import lombok.RequiredArgsConstructor
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.web.bind.annotation.*
import javax.annotation.PostConstruct

private val logger = KotlinLogging.logger {}

@RequiredArgsConstructor
@RestController
@RequestMapping("/callback")
class PaymentPubController (val redisPublisher: RedisPublisher) {

    val om = jacksonObjectMapper()

    /**
     * 발행
     */
    @PostMapping("/imp/publish")
    fun pubMessage(@RequestBody impRequestDto: ImpRequestDto) {
        logger.info { "data: $impRequestDto" }
        val key = CommonDef.IMP_BUS
        val channel: ChannelTopic? = ChannelsAdvice.channels[key]
        var jsonStr = om.writeValueAsString(impRequestDto)
        if (channel !== null) {
            redisPublisher.publish(channel, jsonStr)
        } else {
            throw ErrorException(ErrorCode.NO_CHANNEL)
        }
    }
}