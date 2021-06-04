package com.day1.callback.web

import com.day1.callback.aspect.ChannelsAdvice
import com.day1.callback.service.exception.ErrorCode
import com.day1.callback.service.exception.ErrorException
import com.day1.callback.service.redis.impl.RedisMessageDtoSubscriber
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import lombok.RequiredArgsConstructor
import mu.KotlinLogging
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger {}

@RequiredArgsConstructor
@RestController
@RequestMapping("/callback")
class PubSubController (val redisMessageDtoSubscriber: RedisMessageDtoSubscriber,
                        val redisMessageListenerContainer: RedisMessageListenerContainer) {

    /**
     * 채널 조회
     */
    @GetMapping("/channels")
    fun findAllChannels(): MutableSet<String> {
        return ChannelsAdvice.channels.keys
    }

    /**
     * 구독 시작
     */
    @PutMapping("/subscribe/start/{key}")
    fun subMessage(@PathVariable key: String) {
        logger.info { "subscribe start" }
        val channel = ChannelsAdvice.channels[key]?: throw ErrorException(ErrorCode.NO_CHANNEL)
        redisMessageListenerContainer.addMessageListener(redisMessageDtoSubscriber,  channel)
    }

    /**
     * 구독 삭제
     */
    @PutMapping("/subscribe/stop/{key}")
    fun unsubMessage(@PathVariable key: String) {
        logger.info { "subscribe stop" }
        val channel = ChannelsAdvice.channels[key]?: throw ErrorException(ErrorCode.NO_CHANNEL)
        redisMessageListenerContainer.removeMessageListener(redisMessageDtoSubscriber,  channel)
    }

    /**
     * 신규 topic 생성
     */
    @PutMapping("/channel/{key}")
    fun createImpChannel(@PathVariable key: String) {
        logger.info { "create channel : $key" }
        val channel: ChannelTopic = ChannelTopic(key)
        ChannelsAdvice.channels[key] = channel
    }
}