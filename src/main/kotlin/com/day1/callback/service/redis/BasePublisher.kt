package com.day1.callback.service.redis

import org.springframework.data.redis.listener.ChannelTopic

interface BasePublisher {
    fun publisher(topic: ChannelTopic, message: String)
}
