package com.yapp.cvs.api.common.dto

data class OffsetPageDTO <T>(
    val lastId: Long? = null,
    val content: List<T>
) {
}