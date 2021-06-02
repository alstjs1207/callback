package com.day1.callback.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

//@Configuration
//@EnableRedisRepositories
//@EnableCaching
class RedisConfig : CachingConfigurerSupport() {
//    @Value("\${spring.redis.host}")
//    private val redisHost: String? = null
//
//    @Value("\${spring.redis.port}")
//    private val redisPort: Int = 0
//
//    @Bean()
//    fun lettuceConnectionFactory(): RedisConnectionFactory {
//
//
//        return LettuceConnectionFactory(redisHost!!, redisPort)
//    }
//
//    @Bean("redisTemplateWithLettuce")
//    fun redisTemplateWithLettuce(
//
//    ): RedisTemplate<*, *> {
//
//        val template = RedisTemplate<String, Any>()
//        template.keySerializer = StringRedisSerializer()
//        template.valueSerializer = GenericJackson2JsonRedisSerializer()
//        template.hashKeySerializer = StringRedisSerializer()
//        template.hashValueSerializer = GenericJackson2JsonRedisSerializer()
//
//        template.setConnectionFactory(lettuceConnectionFactory())
//        template.setEnableTransactionSupport(true)
//
//
//        return template
//    }
}
