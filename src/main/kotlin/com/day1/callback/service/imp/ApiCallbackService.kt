package com.day1.callback.service.imp

import com.day1.callback.service.CallbackService
import com.day1.callback.service.strategy.ImpApiStrategy
import com.day1.callback.service.strategy.KollusApiStrategy
import com.day1.callback.web.dto.ImpRequestDto
import org.springframework.stereotype.Service

@Service
class ApiCallbackService {
    var impApiStrategy = ImpApiStrategy()
    var kollusApiStrategy = KollusApiStrategy()

    fun test() {
        // 전략에 따라 다른 결과
        val callbackService = CallbackService(kollusApiStrategy)
        println(callbackService.callbackDay1())

        callbackService.changeApiStrategy(impApiStrategy)
        println(callbackService.callbackDay1())
    }


}