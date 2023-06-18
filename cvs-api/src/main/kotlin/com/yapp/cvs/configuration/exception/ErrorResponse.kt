package com.yapp.cvs.configuration.exception

import java.lang.Exception

data class ErrorResponse(
    val code: String,
    val reason: String
) {
    companion object {
        fun from(exception: PyunleeCodeException): ErrorResponse {
            return ErrorResponse(exception.code, exception.reason)
        }

        fun from(exception: Exception): ErrorResponse {
            return ErrorResponse("GLOBAL_500_001", exception.message!!)
        }
    }
}
