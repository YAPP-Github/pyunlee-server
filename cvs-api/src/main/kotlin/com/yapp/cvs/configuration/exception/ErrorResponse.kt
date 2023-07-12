package com.yapp.cvs.configuration.exception

import java.time.LocalDateTime

data class ErrorResponse(
    val error: String?,
    val path: String,
    val timestamp: LocalDateTime = LocalDateTime.now()
)