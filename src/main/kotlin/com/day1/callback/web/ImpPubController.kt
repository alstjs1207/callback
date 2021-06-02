package com.day1.callback.web

import com.day1.callback.domain.redis.Imp
import com.day1.callback.service.redis.RedisPublisher
import com.day1.callback.service.redis.RedisSubscriber
import lombok.RequiredArgsConstructor
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.web.bind.annotation.*
import javax.annotation.PostConstruct

@RequiredArgsConstructor
@RestController
@RequestMapping("/pub")
class ImpPubController (val redisPublisher: RedisPublisher,
                        val redisSubscriber: RedisSubscriber,
                        val redisMessageListenerContainer: RedisMessageListenerContainer,
                        var channels: HashMap<String, ChannelTopic>) {
    companion object: KLogging()

    @Value("\${spring.redis.channel}")
    private val redisChannel: String? = null

    @PostConstruct
    fun init() {
        logger.info { "init!!!!!" }
        logger.info { "$redisChannel" }
        channels = mutableMapOf<String, ChannelTopic>() as HashMap<String, ChannelTopic>
        //callback에 필요한 channel 등록
        if( redisChannel !== null) {
            val keys = redisChannel.split(",")
            for(key in keys) {
                val channel: ChannelTopic = ChannelTopic(key)
                //redisMessageListenerContainer.addMessageListener(redisSubscriber, channel)
                channels[key] = channel
            }
        }
    }

    @GetMapping("/imp/channels")
     fun findAllChannels(): MutableSet<String> {
        return channels.keys
    }

    /**
     * 신규 topic 생성 및 Listener 등록
     */
    @PutMapping("/imp/{key}")
    fun createImpChannel(@PathVariable key: String) {
        logger.info { "create channel : $key" }
        val channel: ChannelTopic = ChannelTopic(key)
        //redisMessageListenerContainer.addMessageListener(redisSubscriber, channel)
        channels[key] = channel

    }

    @PostMapping("/imp")
    fun pubMessage(@RequestBody imp: Imp) {
        logger.info { "data: $imp" }
        val key = "bus:0:pg:imp"
        val channel: ChannelTopic? = channels[key]
        logger.info { "channel : $channel" }
        if (channel !== null) {
            redisPublisher.publish(channel, imp)
        }
    }
}