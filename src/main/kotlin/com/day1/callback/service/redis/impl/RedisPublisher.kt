package com.day1.callback.service.redis.impl

import com.day1.callback.aspect.ChannelsAdvice
import com.day1.callback.config.DaumRedisConfig
import com.day1.callback.service.exception.ErrorCode
import com.day1.callback.service.exception.ErrorException
import com.day1.callback.service.redis.*
import com.day1.callback.util.CommonDef
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.stereotype.Service

@Service
class RedisPublisher(
    val channelsAdvice: ChannelsAdvice,
    val publisherMachine: PublisherMachine
) {

    private fun publish(topic: ChannelTopic, message: String, site: String) {
        val basePublisher: BasePublisher = publisherMachine.changeRedisTemplate(site)
        basePublisher.publisher(topic, message)
    }

    fun runPublish(site: String, channelName: String, jsonStr: String) {
        val key = channelsAdvice.toChannelName(channelName)
        val channel: ChannelTopic? = ChannelsAdvice.channels[key]
        if (channel !== null) {
            publish(channel, jsonStr, site)
        } else {
            //채널 생성 후 발행
            val newChannel: ChannelTopic = ChannelTopic(key)
            ChannelsAdvice.channels[key] = newChannel
            publish(newChannel, jsonStr, site)
        }
    }
}