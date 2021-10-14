package com.day1.callback.service.redis.impl

import com.day1.callback.aspect.ChannelsAspect
import mu.KotlinLogging
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class RedisSubscriber(val channelsAspect: ChannelsAspect,
                      val redisMessageSubscriber: RedisMessageSubscriber,
                      val redisMessageListenerContainer: RedisMessageListenerContainer
) {

    fun startSubscribe() {
        val channels = ChannelsAspect.channels.values
        for (channel in channels) {
            logger.info { "start $channel Subscribe" }
            redisMessageListenerContainer.addMessageListener(redisMessageSubscriber, channel)
        }
    }
}