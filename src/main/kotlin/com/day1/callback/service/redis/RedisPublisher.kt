package com.day1.callback.service.redis

import com.day1.callback.domain.redis.Imp
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.stereotype.Service

@Service
class RedisPublisher(val redisTemplate: RedisTemplate<String, String>) {
    val om = jacksonObjectMapper()

    fun publish(topic: ChannelTopic, imp: Imp) {

        var jsonStr = om.writeValueAsString(imp)
        redisTemplate.convertAndSend(topic.topic, jsonStr)
    }
}