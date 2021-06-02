package com.day1.callback.service.redis.impl

import com.day1.callback.service.exception.ImpException
import com.day1.callback.service.redis.RedisSubscriber
import com.day1.callback.web.dto.ImpRequestDto
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import mu.KotlinLogging
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.lang.Exception

private val logger = KotlinLogging.logger {}

@Service
class RedisSubscriberImpl(val redisTemplate: RedisTemplate<String, Any>): RedisSubscriber {
    val om = jacksonObjectMapper()

    @Throws(ImpException::class)
    override fun onMessage(message: Message, pattern: ByteArray?) {
        val body = redisTemplate.stringSerializer.deserialize(message.body)
        val imp: ImpRequestDto = om.readValue(body, ImpRequestDto::class.java)
        logger.info { "imp message = ${imp.toString()}" }
    }
}