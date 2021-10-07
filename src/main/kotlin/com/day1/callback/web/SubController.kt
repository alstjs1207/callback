package com.day1.callback.web

import com.day1.callback.aspect.ChannelsAdvice
import com.day1.callback.service.exception.ErrorCode
import com.day1.callback.service.exception.ErrorException
import com.day1.callback.service.redis.impl.RedisMessageDtoSubscriber
import com.day1.callback.service.redis.impl.RedisMessageSubscriber
import lombok.RequiredArgsConstructor
import mu.KotlinLogging
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger {}

@RequiredArgsConstructor
@RestController
@RequestMapping("/callback")
class SubController(
    val redisMessageDtoSubscriber: RedisMessageDtoSubscriber,
    val channelsAdvice: ChannelsAdvice,
    val redisMessageListenerContainer: RedisMessageListenerContainer
) {

    /**
     * 구독 시작
     */
    @PutMapping("/subscribe/start/{key}")
    fun subMessage(@PathVariable key: String) {
        logger.info { "subscribe $key start" }
        val channel =
            ChannelsAdvice.channels[channelsAdvice.toChannelName(key)] ?: throw ErrorException(ErrorCode.NO_CHANNEL)
        redisMessageListenerContainer.addMessageListener(redisMessageDtoSubscriber, channel)
    }

    /**
     * 구독 삭제
     */
    @PutMapping("/subscribe/stop/{key}")
    fun unsubMessage(@PathVariable key: String) {
        logger.info { "subscribe $key stop" }
        val channel =
            ChannelsAdvice.channels[channelsAdvice.toChannelName(key)] ?: throw ErrorException(ErrorCode.NO_CHANNEL)
        redisMessageListenerContainer.removeMessageListener(redisMessageDtoSubscriber, channel)
    }
}