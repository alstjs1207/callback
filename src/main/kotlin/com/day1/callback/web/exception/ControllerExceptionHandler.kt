package com.day1.callback.web.exception

import com.day1.callback.service.exception.ImpException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler

import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerExceptionHandler {

    @ExceptionHandler(value = [ImpException::class,])
    fun doHandleException(ex: Exception): ResponseEntity<Body> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Body(ex.message.orEmpty()))
    }

    data class Body(val message: String)
}