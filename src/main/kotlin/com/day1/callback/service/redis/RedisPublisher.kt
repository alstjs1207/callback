package com.day1.callback.service.redis

import com.day1.callback.service.exception.ImpException
import com.day1.callback.web.dto.ImpRequestDto
import org.springframework.data.redis.listener.ChannelTopic

interface RedisPublisher {

    @Throws(ImpException::class)
    fun publish(topic: ChannelTopic, impRequestDto: ImpRequestDto)
}