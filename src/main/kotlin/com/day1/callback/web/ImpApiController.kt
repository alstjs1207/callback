package com.day1.callback.web

import com.day1.callback.service.imp.ImpService
import com.day1.callback.web.dto.ImpRequestDto
import com.day1.callback.web.dto.ImpResponseDto
import mu.KLogging
import org.springframework.web.bind.annotation.*

/**
 * 결제 callback Controller
 */
@RestController
@RequestMapping("/pg")
class ImpApiController(val impService: ImpService) {
    companion object: KLogging()

    @PostMapping("/imp")
    fun iamportCallbackHandler(@RequestBody impRequestDto: ImpRequestDto) {
        logger.info { "controller!!!" }
        impService.callbackData(impRequestDto)
    }
}