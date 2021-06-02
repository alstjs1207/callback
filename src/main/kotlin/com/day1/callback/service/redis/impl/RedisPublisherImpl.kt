package com.day1.callback.service.redis.impl

import com.day1.callback.service.redis.RedisPublisher
import com.day1.callback.web.dto.ImpRequestDto
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.stereotype.Service

@Service
class RedisPublisherImpl(val redisTemplate: RedisTemplate<String, String>): RedisPublisher {
    val om = jacksonObjectMapper()

    override fun publish(topic: ChannelTopic, impRequestDto: ImpRequestDto) {
        var jsonStr = om.writeValueAsString(impRequestDto)
        redisTemplate.convertAndSend(topic.topic, jsonStr)
    }
}