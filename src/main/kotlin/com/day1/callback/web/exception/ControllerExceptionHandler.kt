package com.day1.callback.web.exception

import com.day1.callback.service.exception.ErrorCode
import com.day1.callback.service.exception.ErrorException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler

import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerExceptionHandler {
    @ExceptionHandler(value = [ErrorException::class])
    fun errorException(ex: ErrorException): ResponseEntity<ErrorEntity> {
        val errorCode: ErrorCode = ex.getErrorCode()

        return ResponseEntity.status(errorCode.status).body(ErrorEntity(errorCode.rtnCode, errorCode.rtnMsg))
    }

    data class ErrorEntity(val rtnCode: String, val rtnMsg: String)
}