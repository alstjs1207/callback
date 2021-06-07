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

    @Value("\${spring.redis.prefix}")
    private val redisPrefix: String? = "bus"

    @Value("\${spring.redis.database}")
    private val redisDb: Int? = 0

    @PostConstruct
    private fun init() {
        channels = mutableMapOf<String, ChannelTopic>() as HashMap<String, ChannelTopic>
    }

    fun toChannelName(topic: String): String {
        return "$redisPrefix:$redisDb:$topic"
    }
}