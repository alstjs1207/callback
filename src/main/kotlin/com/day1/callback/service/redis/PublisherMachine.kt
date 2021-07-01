package com.day1.callback.service.redis

import org.springframework.data.redis.listener.ChannelTopic

class PublisherMachine(private var basePublisher: BasePublisher) {

    fun changeRedisTemplate(basePublisher: BasePublisher) {
        this.basePublisher = basePublisher
    }

    fun publisher(topic: ChannelTopic, message: String) {
        basePublisher.publisher(topic, message)
    }
}