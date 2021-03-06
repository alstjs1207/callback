package com.day1.callback.web

import com.day1.callback.aspect.ChannelsAspect
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
/*테스트 프레임워크에서의 생성자 매개변수 관리는 스프링 컨테이너가 아닌Jupiter가 담당
  @Autowired를 명시적으로 선언해 주어야 Jupiter가 Spring Contaimer에게 빈 주입을 요청 할 수 있음
*/
@AutoConfigureMockMvc //설정 필요
class PubSubControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val channelsAspect: ChannelsAspect
) {
    @LocalServerPort
    private var port: Int = 0

    @BeforeAll
    fun setup() {
        println(">>> Setup")
        val channel = ChannelTopic(channelsAspect.toChannelName("foo"))
        ChannelsAspect.channels[channelsAspect.toChannelName("foo")] = channel
    }

    @Test
    fun `모든 채널 조회`() {

        val url = "http://localhost:" + port + "/callback/channels"

        //when
        val result: MvcResult = mockMvc.perform(
            MockMvcRequestBuilders
                .get(url)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        assertThat(result.response.contentAsString).contains("bus:0:foo")

    }

    @Test
    fun `채널 생성`() {
        //given
        val key = "bus:0:bar"
        val url = "http://localhost:" + port + "/callback/channel/" + key

        //when
        val result: MvcResult = mockMvc.perform(
            MockMvcRequestBuilders
                .put(url)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        //then
        assertThat(result.response.contentAsString).contains("bus:0:bar")
    }

    @AfterAll
    fun teardown() {
        println(">> Tear down")
    }
}