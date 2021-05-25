package com.day1.callback.web

import com.day1.callback.service.imp.ImpService
import com.day1.callback.web.dto.ImpRequestDto
import com.day1.callback.web.dto.ImpResponseDto
import lombok.RequiredArgsConstructor
import mu.KLogging
import org.springframework.web.bind.annotation.*

@RequiredArgsConstructor
@RestController
@RequestMapping("/pg")
class ImpApiController(val impService: ImpService) {
    companion object: KLogging()

    @GetMapping("/")
    fun index(): String {
        val hello = "Hello world"
        return hello
    }

    @PostMapping("/imp")
    fun iamportCallbackHandler(@RequestBody impRequestDto: ImpRequestDto): ImpResponseDto {

        return impService.callbackData(impRequestDto)
    }
}