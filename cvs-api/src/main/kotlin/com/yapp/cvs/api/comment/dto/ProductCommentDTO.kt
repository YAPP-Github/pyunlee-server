package com.yapp.cvs.api.comment.dto

import com.yapp.cvs.domain.comment.vo.ProductCommentHistoryVO

data class ProductCommentDTO(
        val productCommentHistoryId: Long,
        val productId: Long,
        val memberId: Long,
        val content: String
) {
    companion object {
        fun from(productCommentVO: ProductCommentHistoryVO): ProductCommentDTO {
            return ProductCommentDTO(
                    productCommentHistoryId = productCommentVO.productCommentHistoryId,
                    productId = productCommentVO.productId,
                    memberId = productCommentVO.memberId,
                    content = productCommentVO.content
            )
        }
    }
}