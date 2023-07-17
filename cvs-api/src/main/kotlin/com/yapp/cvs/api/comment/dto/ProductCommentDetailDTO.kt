package com.yapp.cvs.api.comment.dto

import com.yapp.cvs.domain.comment.vo.ProductCommentDetailVO
import com.yapp.cvs.domain.enums.ProductLikeType
import java.time.LocalDateTime

data class ProductCommentDetailDTO(
    val productCommentId: Long,
    val memberId: Long,
    val memberNickName: String,
    val content: String,
    val likeCount: Long?,
    val createdAt: LocalDateTime,
    val productId: Long,
    val productName: String,
    val productBrandName: String,
    val productImageUrl: String?,
    val productLikeType: String
) {
    companion object {
        fun from(productCommentDetailVO: ProductCommentDetailVO): ProductCommentDetailDTO {
            return ProductCommentDetailDTO(
                productCommentId = productCommentDetailVO.productCommentId,
                memberId = productCommentDetailVO.memberId,
                memberNickName = productCommentDetailVO.memberNickName,
                content = productCommentDetailVO.content,
                likeCount = productCommentDetailVO.likeCount,
                createdAt = productCommentDetailVO.createdAt,
                productId = productCommentDetailVO.productId,
                productName = productCommentDetailVO.productName,
                productBrandName = productCommentDetailVO.productBrandName,
                productImageUrl = productCommentDetailVO.productImageUrl,
                productLikeType = productCommentDetailVO.productLikeType.name
            )
        }
    }
}