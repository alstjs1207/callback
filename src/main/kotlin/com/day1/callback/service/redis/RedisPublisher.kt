package com.day1.callback.service.redis

import org.springframework.data.redis.listener.ChannelTopic

interface RedisPublisher {

    fun publish(topic: ChannelTopic, message: String)
}