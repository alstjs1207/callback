package com.day1.callback.service.redis.impl

import com.day1.callback.service.exception.ErrorCode
import com.day1.callback.service.exception.ErrorException
import com.day1.callback.service.redis.RedisPublisher
import com.day1.callback.web.dto.ImpRequestDto
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.stereotype.Service

@Service
class RedisMessagePublisher(val redisTemplate: RedisTemplate<String, String>): RedisPublisher {

    override fun publish(topic: ChannelTopic, message: String) {
        redisTemplate.convertAndSend(topic.topic, message)
    }
}