package com.day1.callback.service.imp

import com.day1.callback.web.dto.ImpRequestDto
import com.day1.callback.web.dto.ImpResponseDto
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import org.springframework.web.client.postForEntity

private val logger = KotlinLogging.logger {}

val mapper = ObjectMapper()
fun Any.convert2ObjectString(): String = mapper.writeValueAsString(this)
fun ResponseEntity<HashMap<String, Any>>.convert2MapBody(): Any? = this.body?.get("body")

@Service
class ImpService {
    fun callbackData(dto: ImpRequestDto) {
        logger.info { "service" }
        //ip Check

        //callback day1
        var resDto = callbackDay1(dto)
        logger.info { "check $resDto" }
    }

    fun callbackDay1(impRequestDto: ImpRequestDto): ImpResponseDto {
        //val restTemplate: RestTemplate()
        val entity : ResponseEntity<HashMap<String, Any>> = RestTemplate().exchange(
            RequestEntity.post("http://localhost:8002/pg/imp")
                .contentType(MediaType.APPLICATION_JSON)
                .body(impRequestDto)
        )

        logger.info { "entity data $entity" }
        val result = ObjectMapper().readValue<ImpResponseDto>(entity.convert2MapBody()?.convert2ObjectString(),ImpResponseDto::class.java)
        //성공
        if(result.imp_success) {
            //이력
            logger.info { "CALLBACK SUCCESS!!" }
        }
        //실패
        else {
            //이력
            logger.error { "CALLBACK FAIL!" }
        }

        return result
    }
}