package com.day1.callback

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@ActiveProfiles("local")
@ExtendWith(SpringExtension::class)
@SpringBootTest
class CallbackApplicationTests {

    @Test
    fun contextLoads() {
    }
}
