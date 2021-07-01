package com.day1.callback.service.redis.impl

import com.day1.callback.aspect.ChannelsAdvice
import com.day1.callback.service.exception.ErrorCode
import com.day1.callback.service.exception.ErrorException
import com.day1.callback.service.redis.*
import com.day1.callback.util.CommonDef
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.stereotype.Service

@Service
class RedisPublisher(
    val daum: DaumPublisher,
    val naver: NaverPublisher,
    val google: GooglePublisher,
    val channelsAdvice: ChannelsAdvice,
) {

    private fun publish(topic: ChannelTopic, message: String, site: String) {
        var publisherMachine = PublisherMachine(daum)

        //어떤 사이트인지에 따라 전략 번경
        when (site) {
            CommonDef.DAUM.name -> {
                publisherMachine.changeRedisTemplate(daum)
            }
            CommonDef.NAVER.name -> {
                publisherMachine.changeRedisTemplate(naver)
            }
            CommonDef.GOOGLE.name -> {
                publisherMachine.changeRedisTemplate(google)
            }
            else -> throw ErrorException(ErrorCode.NO_SITE)
        }
        publisherMachine.publisher(topic, message)

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