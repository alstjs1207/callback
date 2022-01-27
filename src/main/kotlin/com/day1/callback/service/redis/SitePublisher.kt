package com.day1.callback.service.redis

import mu.KotlinLogging
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import javax.annotation.Resource

private val logger = KotlinLogging.logger {}

//TODO 사이트마다 topic을 만들어야할까?
class SitePublisher

class DefaultPublisher(private val defaultRedisTemplate: RedisTemplate<String, Any>) :
    BasePublisher {

    override fun publisher(topic: ChannelTopic, message: String) {
        logger.info { "default pub" }
        defaultRedisTemplate.convertAndSend(topic.topic, message)
    }
}

class NaverPublisher(@Resource(name = "NaverRedisTemplate") val nRedisTemplate: RedisTemplate<String, Any>) :
    BasePublisher {

    override fun publisher(topic: ChannelTopic, message: String) {
        logger.info { "naver pub" }
        nRedisTemplate.convertAndSend(topic.topic, message)}
}

class DaumPublisher(@Resource(name = "DaumRedisTemplate") val dRedisTemplate: RedisTemplate<String, Any>) :
    BasePublisher {

    override fun publisher(topic: ChannelTopic, message: String) {
        logger.info { "daum pub" }
        dRedisTemplate.convertAndSend(topic.topic, message)
    }
}

class GooglePublisher(@Resource(name = "GoogleRedisTemplate") val gRedisTemplate: RedisTemplate<String, Any>) :
    BasePublisher {

    override fun publisher(topic: ChannelTopic, message: String) {
        logger.info { "google pub" }
        gRedisTemplate.convertAndSend(topic.topic, message)
    }
}
