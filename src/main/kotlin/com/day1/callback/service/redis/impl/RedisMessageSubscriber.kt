package com.day1.callback.service.redis.impl

import mu.KotlinLogging
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class RedisMessageSubscriber(): MessageListener {

    override fun onMessage(message: Message, pattern: ByteArray?) {
        logger.info { "message = ${String(message.body)}" }
    }
}