package com.day1.callback.service.redis.impl

import com.day1.callback.aspect.ChannelsAspect
import com.day1.callback.service.redis.*
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.stereotype.Service

@Service
class RedisPublisher(
    val channelsAspect: ChannelsAspect,
    val publisherMachine: PublisherMachine
) {

    private fun publish(topic: ChannelTopic, message: String, site: String) {
        val basePublisher: BasePublisher = publisherMachine.getSiteRedisPublisher(site)
        basePublisher.publisher(topic, message)
    }

    fun runPublish(site: String, channelName: String, jsonStr: String) {
        val key = channelsAspect.toChannelName(channelName)
        val channel: ChannelTopic? = ChannelsAspect.channels[key]
        if (channel !== null) {
            publish(channel, jsonStr, site)
        } else {
            //채널 생성 후 발행
            val newChannel: ChannelTopic = ChannelTopic(key)
            ChannelsAspect.channels[key] = newChannel
            publish(newChannel, jsonStr, site)
        }
    }
}
