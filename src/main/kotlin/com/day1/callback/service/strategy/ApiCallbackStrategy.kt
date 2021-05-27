package com.day1.callback.service.strategy

interface ApiCallbackStrategy {
    fun todoApi() : String
}

class ImpApiStrategy: ApiCallbackStrategy {
    override fun todoApi() = "iamport"
}

class KollusApiStrategy: ApiCallbackStrategy {
    override fun todoApi() = "kollus"
}