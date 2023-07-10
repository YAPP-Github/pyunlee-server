package com.yapp.cvs.domain.base.dto

open class PageSearchFormDTO(
    open val pageNum: Int = 0,
    open val totalPage: Int = 1,
    val pageSize: Int = 10,
) {
}