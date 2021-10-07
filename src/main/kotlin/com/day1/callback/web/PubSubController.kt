package com.day1.callback.web

import com.day1.callback.aspect.ChannelsAdvice
import com.day1.callback.service.exception.ErrorCode
import com.day1.callback.service.exception.ErrorException
import com.day1.callback.service.redis.impl.RedisMessageDtoSubscriber
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import lombok.RequiredArgsConstructor
import mu.KotlinLogging
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.web.bind.annotation.*
import javax.annotation.Resource

private val logger = KotlinLogging.logger {}

@RequiredArgsConstructor
@RestController
@RequestMapping("/callback")
class PubSubController(val channelsAdvice: ChannelsAdvice) {

    /**
     * 모든 채널 조회
     */
    @GetMapping("/channels")
    fun findAllChannels(): MutableSet<String> {
        return ChannelsAdvice.channels.keys
    }

    /**
     * 신규 topic 생성
     */
    @PutMapping("/channel/{key}")
    fun createChannel(@PathVariable key: String): String {
        logger.info { "create $key channel" }
        val channel: ChannelTopic = ChannelTopic(channelsAdvice.toChannelName(key))
        ChannelsAdvice.channels[key] = channel
        return key
    }
}