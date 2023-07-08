package com.yapp.cvs.domain.comment.vo

import com.yapp.cvs.domain.enums.ProductLikeType
import java.time.LocalDateTime

data class ProductCommentDetailVO(
        val productCommentId: Long,
        val content: String,
        val commentLikeCount: Long,
        val createdAt: LocalDateTime,
        val likeType: ProductLikeType ?= ProductLikeType.NONE,
        val productId: Long,

        val memberId: Long,
        val nickname: String,
        var isOwner: Boolean = false
)
