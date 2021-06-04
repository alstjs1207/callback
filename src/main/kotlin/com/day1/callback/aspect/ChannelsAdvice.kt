package com.day1.callback.aspect

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

private val logger = KotlinLogging.logger {}

@Component
class ChannelsAdvice (){

    companion object {
        lateinit var channels: HashMap<String, ChannelTopic>
    }

    @Value("\${spring.redis.channel}")
    lateinit var redisChannels: List<String>


    //TODO 더 좋은 방법이 무엇일까?..
    @PostConstruct
    private fun init() {
        logger.info { "init! $redisChannels" }
        channels = mutableMapOf<String, ChannelTopic>() as HashMap<String, ChannelTopic>
        //callback에 필요한 channel 등록
        for (key in redisChannels) {
            val channel: ChannelTopic = ChannelTopic(key)
            channels[key] = channel
        }
    }
}