package com.day1.callback.web

import com.day1.callback.web.dto.RedisRequestDto
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

// top-level Functions
val mapper = ObjectMapper()
fun Any.convert2ObjectString(): String = mapper.writeValueAsString(this)

@ActiveProfiles("local")
@TestInstance(TestInstance.Lifecycle.PER_CLASS) //BeforeAll, AfterAll 사용
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
/*테스트 프레임워크에서의 생성자 매개변수 관리는 스프링 컨테이너가 아닌Jupiter가 담당
  @Autowired를 명시적으로 선언해 주어야 Jupiter가 Spring Contaimer에게 빈 주입을 요청 할 수 있음
*/
@AutoConfigureMockMvc //설정 필요
class PubControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
) {

    @LocalServerPort
    private var port: Int = 0

    @BeforeAll
    fun setup() {
        println(">>> Setup")
    }

    @Test
    fun `Redis publish`() {

        //given
        val redisRequestDto = RedisRequestDto("foo", "new people")
        val site: String = "NAVER";
        val url = "http://localhost:" + port + "/callback/publish/" + site

        //when
        mockMvc.perform(
            MockMvcRequestBuilders
                .post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper().writeValueAsString(redisRequestDto))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @AfterAll
    fun teardown() {
        println(">> Tear down")
    }
}