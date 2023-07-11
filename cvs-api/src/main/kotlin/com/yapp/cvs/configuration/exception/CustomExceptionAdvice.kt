package com.yapp.cvs.configuration.exception

import com.yapp.cvs.exception.BadRequestException
import com.yapp.cvs.exception.NotFoundSourceException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class CustomExceptionAdvice {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundSourceException::class)
    fun handleNotFoundSourceException(exception: NotFoundSourceException, request: HttpServletRequest): ErrorResponse {
        return ErrorResponse(exception.message, request.requestURI)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(exception: BadRequestException, request: HttpServletRequest): ErrorResponse {
        return ErrorResponse(exception.message, request.requestURI)
    }
}