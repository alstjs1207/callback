package com.day1.callback

import com.day1.callback.web.dto.ImpRequestDto
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForEntity

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ImpApiControllerTest {

    @Test
    fun `day1 callback`() {

        //given
        var impRequestDto = ImpRequestDto(10,"card")

        //when
        var entity : ResponseEntity<String> = RestTemplate().postForEntity(
            "http://192.168.5.102:3001/imp.do",impRequestDto)

        println(entity)


    }

    @Test
    fun `iamport callback`() {
        //given

        //when

        //then

    }
}