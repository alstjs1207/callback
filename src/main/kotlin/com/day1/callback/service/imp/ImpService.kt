package com.day1.callback.service.imp

import com.day1.callback.config.ImpConfigProperties
import com.day1.callback.web.dto.ImpRequestDto
import com.day1.callback.web.dto.ImpResponseDto
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange

private val logger = KotlinLogging.logger {}

val mapper = ObjectMapper()
fun Any.convert2ObjectString(): String = mapper.writeValueAsString(this)
fun ResponseEntity<HashMap<String, Any>>.convert2MapBody(): Any? = this.body?.get("data")

/**
 * 결제 서비스
 */

@Service
class ImpService (private val impConfigProperties: ImpConfigProperties): ImpServiceImpl {

    fun callbackData(dto: ImpRequestDto) {
        logger.info { "service" }
        //ip Check todo Spring Security
        //52.78.100.19, 52.78.48.223

        //callback day1
        var resDto = callbackDay1(dto)
        logger.info { "check $resDto" }

    }

    override fun callbackDay1(impRequestDto: ImpRequestDto): ImpResponseDto {

        val entity : ResponseEntity<HashMap<String, Any>> = RestTemplate().exchange(
            RequestEntity.post(impConfigProperties.endpoint)
                //.header("Authorization",impConfigProperties.token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(impRequestDto)
        )

        logger.info { "entity data $entity" }

        return ObjectMapper().readValue<ImpResponseDto>(entity.convert2MapBody()?.convert2ObjectString(),ImpResponseDto::class.java)
    }
}