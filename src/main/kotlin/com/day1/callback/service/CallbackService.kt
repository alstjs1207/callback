package com.day1.callback.service

import com.day1.callback.service.strategy.ApiCallbackStrategy

class CallbackService (
    private var apiCallbackStrategy: ApiCallbackStrategy
) {

    fun changeApiStrategy(apiCallbackStrategy: ApiCallbackStrategy) {
        this.apiCallbackStrategy = apiCallbackStrategy
    }

    fun callbackDay1(): String {
        return apiCallbackStrategy.todoApi()
    }
}