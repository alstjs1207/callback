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
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit.jupiter.SpringExtension

// top-level Functions
val mapper = ObjectMapper()
fun Any.convert2ObjectString(): String = mapper.writeValueAsString(this)
fun ResponseEntity<HashMap<String, Any>>.convert2MapBody(): Any? = this.body?.get("body")

@TestInstance(TestInstance.Lifecycle.PER_CLASS) //BeforeAll, AfterAll 사용
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
        val url2 ="http://localhost:8002/pg/imp"

        //when
        val entity : ResponseEntity<HashMap<String, Any>> = restTemplate.exchange(
            RequestEntity.post(url2)
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