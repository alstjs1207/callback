package com.day1.callback.service.exception

class ErrorException(private val errorCode: ErrorCode) : RuntimeException(errorCode.rtnMsg) {

    companion object {
        private const val serialVersionUID = 1L
    }
    fun getErrorCode(): ErrorCode {
        return errorCode
    }
}