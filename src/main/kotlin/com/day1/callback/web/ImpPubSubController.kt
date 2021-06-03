package com.day1.callback.web

import com.day1.callback.service.exception.ErrorCode
import com.day1.callback.service.exception.ErrorException
import com.day1.callback.service.redis.RedisPublisher
import com.day1.callback.service.redis.impl.RedisMessageDtoSubscriber
import com.day1.callback.util.CommonDef
import com.day1.callback.web.dto.ImpRequestDto
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import lombok.RequiredArgsConstructor
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.web.bind.annotation.*
import javax.annotation.PostConstruct

private val logger = KotlinLogging.logger {}

@RequiredArgsConstructor
@RestController
@RequestMapping("/callback")
class ImpPubSubController (val redisPublisher: RedisPublisher,
                           val redisMessageDtoSubscriber: RedisMessageDtoSubscriber,
                           val redisMessageListenerContainer: RedisMessageListenerContainer,
                           var channels: HashMap<String, ChannelTopic>) {

    @Value("\${spring.redis.channel}")
    private val redisChannel: String? = null

    val om = jacksonObjectMapper()

    //TODO 더 좋은 방법이 무엇일까?..
    @PostConstruct
    fun init() {
        logger.info { "init! $redisChannel" }
        channels = mutableMapOf<String, ChannelTopic>() as HashMap<String, ChannelTopic>
        //callback에 필요한 channel 등록
        if(redisChannel !== null) {
            val keys = redisChannel.split(",")
            for (key in keys) {
                val channel: ChannelTopic = ChannelTopic(key)
                channels[key] = channel
            }
        }
    }

    /**
     * 채널 조회
     */
    @GetMapping("/imp/channels")
     fun findAllChannels(): MutableSet<String> {
        return channels.keys
    }

    /**
     * 발행
     */
    @PostMapping("/imp/publish")
    fun pubMessage(@RequestBody impRequestDto: ImpRequestDto) {
        logger.info { "data: $impRequestDto" }
        val key = CommonDef.IMP_BUS
        val channel: ChannelTopic? = channels[key]
        var jsonStr = om.writeValueAsString(impRequestDto)
        if (channel !== null) {
            redisPublisher.publish(channel, jsonStr)
        } else {
            throw ErrorException(ErrorCode.NO_CHANNEL)
        }
    }

    /**
     * 구독 시작
     */
    @PutMapping("/imp/subscribe/start/{key}")
    fun subMessage(@PathVariable key: String) {
        logger.info { "subscribe start" }
        val channel = channels[key]?: throw ErrorException(ErrorCode.NO_CHANNEL)
        redisMessageListenerContainer.addMessageListener(redisMessageDtoSubscriber,  channel)
    }

    /**
     * 구독 삭제
     */
    @PutMapping("/imp/subscribe/stop/{key}")
    fun unsubMessage(@PathVariable key: String) {
        logger.info { "subscribe stop" }
        val channel = channels[key]?: throw ErrorException(ErrorCode.NO_CHANNEL)
        redisMessageListenerContainer.removeMessageListener(redisMessageDtoSubscriber,  channel)
    }

    /**
     * 신규 topic 생성
     */
    @PutMapping("/imp/channel/{key}")
    fun createImpChannel(@PathVariable key: String) {
        logger.info { "create channel : $key" }
        val channel: ChannelTopic = ChannelTopic(key)
        channels[key] = channel
    }
}