package com.yapp.cvs.domain.base.vo

data class OffsetPageVO<T>(
    val lastId: Long? = null,
    val content: List<T>
) {
}