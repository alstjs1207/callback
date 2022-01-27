package com.day1.callback.aspect

import com.day1.callback.service.redis.impl.RedisMessageSubscriber
import mu.KotlinLogging
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class SubscribeRunner(
    val redisMessageSubscriber: RedisMessageSubscriber,
    val redisMessageListenerContainer: RedisMessageListenerContainer
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        val channels = ChannelsAspect.channels.values
        for (channel in channels) {
            logger.info { "start $channel Subscribe" }
            redisMessageListenerContainer.addMessageListener(redisMessageSubscriber, channel)
        }
    }
}
