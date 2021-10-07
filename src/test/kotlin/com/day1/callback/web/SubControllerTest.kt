package com.day1.callback.web

import com.day1.callback.aspect.ChannelsAdvice
import com.day1.callback.web.dto.ImpRequestDto
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ActiveProfiles("local")
@TestInstance(TestInstance.Lifecycle.PER_CLASS) //BeforeAll, AfterAll 사용
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc //설정 필요
class SubControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val channelsAdvice: ChannelsAdvice
) {
    @LocalServerPort
    private var port: Int = 0

    @BeforeAll
    fun setup() {
        println(">>> Setup")
        val channel: ChannelTopic = ChannelTopic(channelsAdvice.toChannelName("pg:imp"))
        ChannelsAdvice.channels[channelsAdvice.toChannelName("pg:imp")] = channel
    }

    @Test
    fun `구독 시작`() {
        //given
        val key = "pg:imp"
        val url = "http://localhost:" + port + "/callback/subscribe/start/" + key

        //when
        mockMvc.perform(
            MockMvcRequestBuilders
                .put(url)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)

        //then
    }

    @Test
    fun `구독 종료`() {
        //given
        val key = "pg:imp"
        val url = "http://localhost:" + port + "/callback/subscribe/stop/" + key

        //when
        mockMvc.perform(
            MockMvcRequestBuilders
                .put(url)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)

        //then
    }

    @AfterAll
    fun teardown() {
        println(">> Tear down")
    }
}