package com.day1.callback.web

import com.day1.callback.service.imp.ApiCallbackService
import com.day1.callback.web.dto.ImpRequestDto
import mu.KLogging
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/test")
class TestApiController(val ApiCallbackService: ApiCallbackService) {
    companion object: KLogging()

    @PostMapping("/imp")
    fun iamportCallbackHandler() {
        logger.info { "controller!!!" }
        ApiCallbackService.test()
    }

    @GetMapping("/kollus")
    fun kollusCallbackHandler() {
        logger.info { "controller!!!" }
        ApiCallbackService.test()
    }
}