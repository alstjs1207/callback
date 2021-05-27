package com.day1.callback.web

import com.day1.callback.web.dto.ImpRequestDto
import com.day1.callback.web.dto.ImpResponseDto
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.exchange
import org.springframework.http.*
import org.springframework.test.context.junit.jupiter.SpringExtension

// top-level Functions
val mapper = ObjectMapper()
fun Any.convert2ObjectString(): String = mapper.writeValueAsString(this)
fun ResponseEntity<HashMap<String, Any>>.convert2MapBody(): Any? = this.body?.get("body")

@TestInstance(TestInstance.Lifecycle.PER_CLASS) //BeforeAll, AfterAll를 위해 사용
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HttpApiTests @Autowired constructor(
                    val restTemplate: TestRestTemplate) {

    @BeforeAll
    fun setup() {
        println(">>> Setup")
    }

    @Test
    fun `call node iamport`() {
        //given
        var impRequestDto = ImpRequestDto("imp_1234567890", "merchant_1234567890", "ready")
        //var impRequestDto = ImpRequestDto("imp_910037693783", "ptx-fc-91-4-20210526021037203", status = "paid")
        // api - callback 호출
        //val url ="https://api.local.fastcampus.co.kr/callback/pg/imp"
        // 기존 callback 호출
        val url2 ="http://localhost:8002/pg/imp"

        //when
        val entity : ResponseEntity<HashMap<String, Any>> = restTemplate.exchange(
            RequestEntity.post(url2)
                //.header("Authorization","bearer 21fc92a79da2b6686a735e6c3aab60b0bbc4")
                //.header("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36")
                .contentType(MediaType.APPLICATION_JSON)
                .body(impRequestDto)
        )
        //then
        println(entity)
        val result = ObjectMapper().readValue<ImpResponseDto>(entity.convert2MapBody()?.convert2ObjectString(),ImpResponseDto::class.java)
        println(result)
        Assertions.assertThat(entity.statusCode).isEqualTo(HttpStatus.ACCEPTED) //202
        Assertions.assertThat(result.imp_uid).isEqualTo(impRequestDto.imp_uid)
        Assertions.assertThat(result.merchant_uid).isEqualTo(impRequestDto.merchant_uid)

    }

    @AfterAll
    fun teardown() {
        println(">> Tear down")
    }
}