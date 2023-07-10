package com.yapp.cvs.domain.base.vo

class PageVO<T>(
    val pageNum: Int,
    val pageSize: Int,
    val totalPage: Int,
    val contents: List<T>,
) {
}