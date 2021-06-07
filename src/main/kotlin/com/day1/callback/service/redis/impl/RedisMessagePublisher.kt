package com.day1.callback.service.redis.impl

import com.day1.callback.service.redis.RedisPublisher
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.stereotype.Service
import javax.annotation.Resource

@Service
class RedisMessagePublisher(@Resource(name = "fcRedisTemplate") val fcRedisTemplate: RedisTemplate<String, Any>,
                            @Resource(name = "sbRedisTemplate") val sbRedisTemplate: RedisTemplate<String, Any>): RedisPublisher {

    override fun fcPublish(topic: ChannelTopic, message: String) {
        fcRedisTemplate.convertAndSend(topic.topic, message)
    }

    override fun sbPublish(topic: ChannelTopic, message: String) {
        sbRedisTemplate.convertAndSend(topic.topic, message)
    }
}