package com.day1.callback.config

import com.day1.callback.service.redis.RedisSubscriber
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.MessageListener
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
@EnableRedisRepositories
class RedisCacheConfig() {
    companion object: KLogging()
    @Value("\${spring.redis.host}")
    private val redisHost: String? = null

    @Value("\${spring.redis.port}")
    private val redisPort: Int = 0

    @Value("\${spring.redis.database}")
    private val database: Int = 0

    @Value("\${spring.redis.channel}")
    private val redisChannel: String? = null

    @Bean
    fun  lettuceConnectionFactory(): RedisConnectionFactory {
        val redisStandaloneConfiguration = RedisStandaloneConfiguration()
        redisStandaloneConfiguration.hostName = redisHost!!
        redisStandaloneConfiguration.port = redisPort
        redisStandaloneConfiguration.database = database
        return LettuceConnectionFactory(redisStandaloneConfiguration)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> {

        val template = RedisTemplate<String, Any>()
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = GenericJackson2JsonRedisSerializer()
//        template.hashKeySerializer = StringRedisSerializer()
//        template.hashValueSerializer = GenericJackson2JsonRedisSerializer()

        template.setConnectionFactory(lettuceConnectionFactory())
        template.setEnableTransactionSupport(true)
        return template
    }

//    @Bean
//    fun topic(): ChannelTopic {
//        logger.info { "channel=== $redisChannel" }
//      return ChannelTopic(redisChannel!!)
//    }
//
//    @Bean
//    fun newMessageListenerAdapter(redisSubscriber: RedisSubscriber): MessageListenerAdapter = MessageListenerAdapter(redisSubscriber)
//
//    @Bean
//    fun redisContainer(messageListenerAdapter: MessageListenerAdapter): RedisMessageListenerContainer {
//        logger.info { "RedisMessageListenerContainer!!" }
//        val container = RedisMessageListenerContainer()
//        container.setConnectionFactory(lettuceConnectionFactory())
//        container.addMessageListener(messageListenerAdapter, topic())
//        return container
//    }
    @Bean
    fun redisContainer(): RedisMessageListenerContainer {
        logger.info { "RedisMessageListenerContainer!!" }
        val container = RedisMessageListenerContainer()
        container.setConnectionFactory(lettuceConnectionFactory())
        return container
    }
}