package com.day1.callback.web

import com.day1.callback.service.redis.RedisPublisher
import com.day1.callback.service.redis.RedisSubscriber
import com.day1.callback.web.dto.ImpRequestDto
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
                           val redisSubscriber: RedisSubscriber,
                           val redisMessageListenerContainer: RedisMessageListenerContainer,
                           var channels: HashMap<String, ChannelTopic>) {

    @Value("\${spring.redis.channel}")
    private val redisChannel: String? = null

    //TODO 더 좋은 방법이 무엇일까?..
    @PostConstruct
    fun init() {
        logger.info { "init!!!!! $redisChannel" }
        channels = mutableMapOf<String, ChannelTopic>() as HashMap<String, ChannelTopic>
        //callback에 필요한 channel 등록
        val keys = redisChannel!!.split(",")
        for(key in keys) {
            val channel: ChannelTopic = ChannelTopic(key)
            channels[key] = channel
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
        val key = "bus:0:pg:imp"
        val channel: ChannelTopic? = channels[key]
        logger.info { "channel : $channel" }
        //TODO null 처리
        if (channel !== null) {
            redisPublisher.publish(channel, impRequestDto)
        }
    }

    /**
     * 구독 시작
     */
    @PutMapping("/imp/subscribe/start/{key}")
    fun subMessage(@PathVariable key: String) {
        logger.info { "subscribe start" }
        redisMessageListenerContainer.addMessageListener(redisSubscriber,  channels[key]!!)
    }

    /**
     * 구독 삭제
     */
    @PutMapping("/imp/subscribe/stop/{key}")
    fun unsubMessage(@PathVariable key: String) {
        logger.info { "subscribe stop" }
        redisMessageListenerContainer.removeMessageListener(redisSubscriber,  channels[key]!!)
    }

    /**
     * 신규 topic 생성 및 Listener 등록
     */
    @PutMapping("/imp/channel/{key}")
    fun createImpChannel(@PathVariable key: String) {
        logger.info { "create channel : $key" }
        val channel: ChannelTopic = ChannelTopic(key)
        channels[key] = channel
    }
}