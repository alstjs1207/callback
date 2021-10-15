package com.day1.callback.service.redis.impl

import mu.KotlinLogging
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service


private val logger = KotlinLogging.logger {}

@Service
class RedisMessageSubscriber(val redisTemplate: RedisTemplate<String, Any>): MessageListener {

    override fun onMessage(message: Message, pattern: ByteArray?) {
        val msg = redisTemplate.stringSerializer.deserialize(message.body)
        val channel: String? = pattern?.let { String(it) }

        if (channel !== null) {

            if(!msg.equals(channel)) {
                logger.info { "channel: $channel message: $msg" }
            }

            if (redisTemplate.opsForList().size(channel)!! > 0) {
                val popMsg = redisTemplate.opsForList().rightPop(channel)
                logger.info { "channel: $channel message: $popMsg" }
            }
        }
    }
}