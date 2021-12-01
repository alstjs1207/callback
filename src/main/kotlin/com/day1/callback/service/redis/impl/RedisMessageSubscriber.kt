package com.day1.callback.service.redis.impl

import com.day1.callback.service.cloudrun.CloudPublisher
import com.day1.callback.service.cloudrun.CloudRunPublisher
import mu.KotlinLogging
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service


private val logger = KotlinLogging.logger {}

@Service
class RedisMessageSubscriber(val redisTemplate: RedisTemplate<String, Any>, val cloudPublisher: CloudPublisher): MessageListener {

    override fun onMessage(message: Message, pattern: ByteArray?) {
        val msg = redisTemplate.stringSerializer.deserialize(message.body)
        val channel: String? = pattern?.let { String(it) }

        if (channel !== null) {

            if(!msg.equals(channel)) {
                //cloud run으로 전송
                cloudPublisher.getCloudPublisher(msg, channel)
                return
            }

            //message와 channel이름이 동일한 경우 redis lpush에 담겨있다.
            while (redisTemplate.opsForList().size(channel)!! > 0) {
                val popMsg = redisTemplate.opsForList().rightPop(channel)
                //cloud run으로 전송
                cloudPublisher.getCloudPublisher(popMsg as String?, channel)
            }
        }
    }
}