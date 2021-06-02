package com.day1.callback.service.redis

import com.day1.callback.domain.redis.Imp
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import mu.KotlinLogging
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.lang.Exception

private val logger = KotlinLogging.logger {}

@Service
class RedisSubscriber(val redisTemplate: RedisTemplate<String, Any>): MessageListener {
    val om = jacksonObjectMapper()

    override fun onMessage(message: Message, pattern: ByteArray?) {
        try {
            val body = redisTemplate.stringSerializer.deserialize(message.body)
            val imp: Imp = om.readValue(body, Imp::class.java)
            logger.info { "imp message = ${imp.toString()}" }
        } catch (e: Exception) {
            logger.error { e.message }
        }
    }
}