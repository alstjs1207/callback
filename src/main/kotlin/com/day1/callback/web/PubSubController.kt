package com.day1.callback.web

import com.day1.callback.aspect.ChannelsAspect
import lombok.RequiredArgsConstructor
import mu.KotlinLogging
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger {}

@RequiredArgsConstructor
@RestController
@RequestMapping("/callback")
class PubSubController(val channelsAspect: ChannelsAspect) {

    /**
     * 모든 채널 조회
     */
    @GetMapping("/channels")
    fun findAllChannels(): MutableSet<String> {
        return ChannelsAspect.channels.keys
    }

    /**
     * 신규 topic 생성
     */
    @PutMapping("/channel/{key}")
    fun createChannel(@PathVariable key: String): String {
        logger.info { "create $key channel" }
        val channel: ChannelTopic = ChannelTopic(channelsAspect.toChannelName(key))
        ChannelsAspect.channels[key] = channel
        return key
    }
}