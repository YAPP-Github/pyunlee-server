package com.yapp.cvs.configuration.exception

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class GlobalExceptionAdvice : ResponseEntityExceptionHandler() {

    @ExceptionHandler(PyunleeCodeException::class)
    fun handlePyunleeCodeException(e: PyunleeCodeException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse.from(e)
        return ResponseEntity.status(400).body(errorResponse)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        log.error("INTERNAL_SERVER_ERROR", e)
        val errorResponse = ErrorResponse.from(e)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(errorResponse)
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)
    }
}
