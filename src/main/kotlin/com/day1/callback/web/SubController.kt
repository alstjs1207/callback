package com.day1.callback.web

import com.day1.callback.aspect.ChannelsAspect
import com.day1.callback.service.exception.ErrorCode
import com.day1.callback.service.exception.ErrorException
import com.day1.callback.service.redis.impl.RedisMessageDtoSubscriber
import com.day1.callback.service.redis.impl.RedisMessageSubscriber
import lombok.RequiredArgsConstructor
import mu.KotlinLogging
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger {}

@RequiredArgsConstructor
@RestController
@RequestMapping("/callback")
class SubController(
    val redisMessageDtoSubscriber: RedisMessageDtoSubscriber,
    val redisMessageSubscriber: RedisMessageSubscriber,
    val channelsAspect: ChannelsAspect,
    val redisMessageListenerContainer: RedisMessageListenerContainer
) {

    /**
     * 구독 시작
     */
    @PutMapping("/subscribe/start/{key}")
    fun subMessage(@PathVariable key: String) {
        logger.info { "subscribe $key start" }
        val channel =
            ChannelsAspect.channels[channelsAspect.toChannelName(key)] ?: throw ErrorException(ErrorCode.NO_CHANNEL)
        redisMessageListenerContainer.addMessageListener(redisMessageSubscriber, channel)
    }

    /**
     * 구독 삭제
     */
    @PutMapping("/subscribe/stop/{key}")
    fun unsubMessage(@PathVariable key: String) {
        logger.info { "subscribe $key stop" }
        val channel =
            ChannelsAspect.channels[channelsAspect.toChannelName(key)] ?: throw ErrorException(ErrorCode.NO_CHANNEL)
        redisMessageListenerContainer.removeMessageListener(redisMessageSubscriber, channel)
    }
}
