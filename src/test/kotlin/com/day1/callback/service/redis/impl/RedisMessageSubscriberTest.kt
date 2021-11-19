package com.day1.callback.service.redis.impl

import com.day1.callback.aspect.ChannelsAspect
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.assertj.core.api.Assertions.assertThat

@ActiveProfiles("local")
@TestInstance(TestInstance.Lifecycle.PER_CLASS) //BeforeAll, AfterAll 사용
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc //설정 필요
class RedisMessageSubscriberTest @Autowired constructor(
    val redisTemplate: RedisTemplate<String, Any>
) {

    @Test
    fun `push and pop `() {
        val data = "{bar:123}"
        redisTemplate.opsForList().leftPush("foo", data)
        val result = redisTemplate.opsForList().rightPop("foo")
        assertThat(data).isEqualTo(result)
    }
}