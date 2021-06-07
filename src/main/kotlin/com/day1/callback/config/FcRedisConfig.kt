package com.day1.callback.config


import com.day1.callback.util.CommonDef
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
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
class FcRedisConfig() {

    @Value("\${spring.redis.fc.host}")
    private val redisHost: String? = null

    @Value("\${spring.redis.fc.port}")
    private val redisPort: Int = 0

    @Value("\${spring.redis.database}")
    private val database: Int = 0


    @Primary
    @Bean( name= ["fcLettuceConnectionFactory"])
    fun  fcLettuceConnectionFactory(): RedisConnectionFactory {
        val redisStandaloneConfiguration = RedisStandaloneConfiguration()
        redisStandaloneConfiguration.hostName = redisHost!!
        redisStandaloneConfiguration.port = redisPort
        redisStandaloneConfiguration.database = database
        return LettuceConnectionFactory(redisStandaloneConfiguration)
    }

    @Primary
    @Bean( name = ["fcRedisTemplate"])
    fun fcRedisTemplate(): RedisTemplate<String, Any> {
        val template = RedisTemplate<String, Any>()
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = GenericJackson2JsonRedisSerializer()
        template.hashKeySerializer = StringRedisSerializer()
        template.hashValueSerializer = GenericJackson2JsonRedisSerializer()
        template.setConnectionFactory(fcLettuceConnectionFactory())
        template.setEnableTransactionSupport(true)
        return template
    }

    @Primary
    @Bean( name= ["fcRedisContainer"])
    fun fcRedisContainer(): RedisMessageListenerContainer {
        val container = RedisMessageListenerContainer()
        container.setConnectionFactory(fcLettuceConnectionFactory())
        return container
    }
}