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
class PubController (val redisPublisher: RedisPublisher,
                     val channelsAdvice: ChannelsAdvice) {

    val om = jacksonObjectMapper()

    /**
     * Fc iamport 발행
     */
    @PostMapping("/imp/publish/fc")
    fun pubFcPaymentCallbackMessage(@RequestBody impRequestDto: ImpRequestDto) {
        logger.info { "data: $impRequestDto" }
        val key =  channelsAdvice.toChannelName(CommonDef.FC+":"+CommonDef.IMP_BUS)
        logger.info { "key: $key" }
        val channel: ChannelTopic? = ChannelsAdvice.channels[key]
        var jsonStr = om.writeValueAsString(impRequestDto)
        if (channel !== null) {
            redisPublisher.fcPublish(channel, jsonStr)
        } else {
            //채널 생성 후 발행
            val channel: ChannelTopic = ChannelTopic(key)
            ChannelsAdvice.channels[key] = channel
            redisPublisher.fcPublish(channel, jsonStr)
            //throw ErrorException(ErrorCode.NO_CHANNEL)
        }
    }

    /**
     * Sb iamport 발행
     */
    @PostMapping("/imp/publish/sb")
    fun pubSbPaymentCallbackMessage(@RequestBody impRequestDto: ImpRequestDto) {
        logger.info { "data: $impRequestDto" }
        val key =  channelsAdvice.toChannelName(CommonDef.SB+":"+CommonDef.IMP_BUS)
        logger.info { "key: $key" }
        val channel: ChannelTopic? = ChannelsAdvice.channels[key]
        var jsonStr = om.writeValueAsString(impRequestDto)
        if (channel !== null) {
            redisPublisher.sbPublish(channel, jsonStr)
        } else {
            //채널 생성 후 발행
            val channel: ChannelTopic = ChannelTopic(key)
            ChannelsAdvice.channels[key] = channel
            redisPublisher.sbPublish(channel, jsonStr)
            //throw ErrorException(ErrorCode.NO_CHANNEL)
        }
    }
}