package com.day1.callback.service.imp

import com.day1.callback.web.dto.ImpRequestDto
import com.day1.callback.web.dto.ImpResponseDto
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForEntity

private val logger = KotlinLogging.logger {}

@Service
class ImpService {

    fun callbackData(dto: ImpRequestDto): ImpResponseDto? {
        logger.info { "service" }
        //ip Check
        //callback day1
        var resDto = callbackDay1(dto)
        logger.info { "check $resDto" }
        return resDto
    }

    fun callbackDay1(impRequestDto: ImpRequestDto): ImpResponseDto? {
        val entity: ResponseEntity<String> = RestTemplate().postForEntity(
            "http://localhost:3001/imp.do",impRequestDto)

        logger.info { "entity data $entity" }


        //성공

        //실패

        return entity.body?.let { jacksonObjectMapper().readValue(it) }
    }
}