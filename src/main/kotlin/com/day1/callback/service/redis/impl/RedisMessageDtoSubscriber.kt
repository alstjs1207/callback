package com.day1.callback.service.redis.impl

import com.day1.callback.web.dto.ImpRequestDto
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import mu.KotlinLogging
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import javax.annotation.Resource

private val logger = KotlinLogging.logger {}

@Service
class RedisMessageDtoSubscriber(val redisTemplate: RedisTemplate<String, Any>): MessageListener {
    val om = jacksonObjectMapper()

    override fun onMessage(message: Message, pattern: ByteArray?) {
        val body = redisTemplate.stringSerializer.deserialize(message.body)
        val imp: ImpRequestDto = om.readValue(body, ImpRequestDto::class.java)
        logger.info { "imp message = ${imp.toString()}" }

    }
}