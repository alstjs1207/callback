package com.day1.callback.service.exception

class CommonException(private val code: Int, override val message: String) : Exception(message) {

    fun getCode(): Int {
        return code
    }
}