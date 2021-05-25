package com.day1.callback.service.imp

import com.day1.callback.web.dto.ImpRequestDto
import com.day1.callback.web.dto.ImpResponseDto
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForEntity

private val logger = KotlinLogging.logger {}

@Service
class ImpService {

    fun callbackData(dto: ImpRequestDto): ImpResponseDto {
        logger.info { "service" }

        //ip Check
        var check = callbackDay1(dto)
        //local env Check
        logger.info { "check $check" }
        dto.ip = 127
        dto.method = "json"

        return ImpResponseDto(dto.ip, dto.method)
    }

    fun callbackDay1(impRequestDto: ImpRequestDto): String {

        val entity: ResponseEntity<String> = RestTemplate().postForEntity(
            "http://192.168.5.102:3001/imp.do",impRequestDto)

        logger.info { "entity data $entity" }
        return "true"
    }
}