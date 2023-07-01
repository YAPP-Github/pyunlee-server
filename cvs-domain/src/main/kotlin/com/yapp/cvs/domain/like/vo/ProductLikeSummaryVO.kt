package com.yapp.cvs.domain.like.vo

data class ProductLikeSummaryVO(
        val productId: Long,
        val likeCount: Long,
        val dislikeCount: Long,
        val totalCount: Long
)