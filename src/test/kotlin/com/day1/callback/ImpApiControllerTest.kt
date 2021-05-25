package com.day1.callback

import com.day1.callback.web.dto.ImpRequestDto
import com.day1.callback.web.dto.ImpResponseDto
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.exchange
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import org.springframework.web.client.postForEntity

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
/*테스트 프레임워크에서의 생성자 매개변수 관리는 스프링 컨테이너가 아닌Jupiter가 담당
  @Autowired를 명시적으로 선언해 주어야 Jupiter가 Spring Contaimer에게 빈 주입을 요청 할 수 있음
*/
@AutoConfigureMockMvc //설정 필요
class ImpApiControllerTest @Autowired constructor(
                           val mockMvc: MockMvc,
                           val restTemplate: TestRestTemplate //test code 에서는 TestRestTemplate 사용
) {

    @LocalServerPort private var port: Int = 0

    @Test
    fun `call-local-call-node-callback-imp`() {
        //given
        var impRequestDto = ImpRequestDto("imp_1234567890",
            "merchant_1234567890",
            status = "ready")
        var url = "http://localhost:3001/imp.do"
        val url2 ="http://localhost:8002/pg/imp"

        //when
        val entity : ResponseEntity<HashMap<String, Object>> = restTemplate.exchange(
            RequestEntity.post(url2)
                .contentType(MediaType.APPLICATION_JSON)
                .body(impRequestDto)
        )
        println(entity.body?.get("body"))
        //val entity : ResponseEntity<String> = RestTemplate().postForEntity(url2,impRequestDto)
        //val impResponseDto = entity.body?.let { jacksonObjectMapper().readValue<ImpResponseDto>(it) }
//        if (impResponseDto != null) {
//            assertThat(impResponseDto.imp_success).isEqualTo(true)
//            assertThat(impResponseDto.imp_uid).isEqualTo(impRequestDto.imp_uid)
//            assertThat(impResponseDto.merchant_uid).isEqualTo(impRequestDto.merchant_uid)
//        }
        assertThat(entity.statusCode).isEqualTo(HttpStatus.ACCEPTED)
    }

    @Test
    fun `day1 callback api`() {
        //given
        val impRequestDto = ImpRequestDto("imp_123","m_123",status = "paid")
        val url = "http://localhost:" + port + "/pg/imp"
        //when
        mockMvc.perform(MockMvcRequestBuilders
            .post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonObjectMapper().writeValueAsString(impRequestDto)))
            .andExpect(MockMvcResultMatchers.status().isOk)

        //then

    }
}