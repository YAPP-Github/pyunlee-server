package com.yapp.cvs.api.comment.dto

import com.yapp.cvs.domain.comment.vo.ProductCommentVO
import java.time.LocalDateTime

data class ProductCommentDTO(
    val productCommentId: Long,
    val memberId: Long,
    val memberNickName: String,
    val content: String,
    val likeCount: Long,
    val createdAt: LocalDateTime,
    val productId: Long
) {
    companion object {
        fun from(productCommentVO: ProductCommentVO): ProductCommentDTO {
            return ProductCommentDTO(
                productCommentId = productCommentVO.productCommentId,
                memberId = productCommentVO.memberId,
                memberNickName = productCommentVO.memberNickName,
                content = productCommentVO.content,
                likeCount = productCommentVO.likeCount,
                createdAt = productCommentVO.createdAt,
                productId = productCommentVO.productId
            )
        }
    }
}