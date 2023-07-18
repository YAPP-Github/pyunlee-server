package com.yapp.cvs.api.comment.dto

import com.yapp.cvs.domain.comment.vo.ProductCommentVO
import java.time.LocalDateTime

data class ProductCommentDTO(
    val productCommentId: Long,
    val memberId: Long,
    val memberNickName: String,
    val content: String,
    val likeCount: Long,
    val isOwner: Boolean,
    val liked: Boolean,
    val createdAt: LocalDateTime,
    val productId: Long,
    val productLikeType: String
) {
    companion object {
        fun from(productCommentVO: ProductCommentVO): ProductCommentDTO {
            return ProductCommentDTO(
                productCommentId = productCommentVO.productCommentId,
                memberId = productCommentVO.memberId,
                memberNickName = productCommentVO.memberNickName,
                content = productCommentVO.content,
                likeCount = productCommentVO.likeCount,
                isOwner = productCommentVO.isOwner,
                liked = productCommentVO.liked,
                createdAt = productCommentVO.createdAt,
                productId = productCommentVO.productId,
                productLikeType = productCommentVO.productLikeType.name
            )
        }
    }
}