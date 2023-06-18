package com.yapp.cvs.configuration.exception

import java.lang.RuntimeException

open class PyunleeCodeException(
    private val errorCode: BaseErrorCode
) : RuntimeException() {
    val code = errorCode.code
    val reason = errorCode.reason
}
