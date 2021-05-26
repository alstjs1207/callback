package com.day1.callback.web

import com.day1.callback.service.imp.ImpService
import com.day1.callback.web.dto.ImpRequestDto
import com.day1.callback.web.dto.ImpResponseDto
import mu.KLogging
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class PingController() {
    @GetMapping("/ping")
    fun ping(): String {
        val hello = "Hello world"
        return hello
    }
}

@RestController
@RequestMapping("/api/pg")
class ImpController(val impService: ImpService) {
    companion object: KLogging()

    @PostMapping("/imp")
    fun iamportCallbackHandler(@RequestBody impRequestDto: ImpRequestDto): ImpResponseDto? {
        logger.info { "iamport controller" }
        return impService.callbackData(impRequestDto)
    }
}

