package com.day1.callback.config


import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
@EnableRedisRepositories
class SbRedisConfig() {
    @Value("\${spring.redis.sb.host}")
    private val redisHost: String? = null

    @Value("\${spring.redis.sb.port}")
    private val redisPort: Int = 0

    @Value("\${spring.redis.database}")
    private val database: Int = 0

    @Bean( name= ["sbLettuceConnectionFactory"])
    fun  sbLettuceConnectionFactory(): RedisConnectionFactory {
        val redisStandaloneConfiguration = RedisStandaloneConfiguration()
        redisStandaloneConfiguration.hostName = redisHost!!
        redisStandaloneConfiguration.port = redisPort
        redisStandaloneConfiguration.database = database
        return LettuceConnectionFactory(redisStandaloneConfiguration)
    }

    @Bean( name = ["sbRedisTemplate"])
    fun sbRedisTemplate(): RedisTemplate<String, Any> {
        val template = RedisTemplate<String, Any>()
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = GenericJackson2JsonRedisSerializer()
        template.hashKeySerializer = StringRedisSerializer()
        template.hashValueSerializer = GenericJackson2JsonRedisSerializer()
        template.setConnectionFactory(sbLettuceConnectionFactory())
        template.setEnableTransactionSupport(true)
        return template
    }

    @Bean( name= ["sbRedisContainer"])
    fun sbRedisContainer(): RedisMessageListenerContainer {
        val container = RedisMessageListenerContainer()
        container.setConnectionFactory(sbLettuceConnectionFactory())
        return container
    }
}