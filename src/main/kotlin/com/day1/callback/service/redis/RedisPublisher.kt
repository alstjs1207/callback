package com.day1.callback.service.redis

import org.springframework.data.redis.listener.ChannelTopic

interface RedisPublisher {

    fun fcPublish(topic: ChannelTopic, message: String)

    fun sbPublish(topic: ChannelTopic, message: String)
}