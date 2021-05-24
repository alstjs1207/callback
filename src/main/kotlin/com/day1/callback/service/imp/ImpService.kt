package com.day1.callback.service.imp

import com.day1.callback.web.dto.ImpRequestDto
import com.day1.callback.web.dto.ImpResponseDto
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class ImpService {

    fun callbackData(dto: ImpRequestDto): ImpResponseDto {
        logger.debug("service")

        //ip Check

        //local env Check

        dto.ip = 127
        dto.method = "json"

        return ImpResponseDto(dto.ip, dto.method)
    }
}