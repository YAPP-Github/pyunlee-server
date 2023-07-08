package com.yapp.cvs.api.comment.dto

import com.yapp.cvs.domain.comment.vo.ProductCommentVO

data class ProductCommentDTO(
        val productCommentId: Long,
        val productId: Long,
        val memberId: Long,
        val content: String
) {
    companion object {
        fun from(productCommentVO: ProductCommentVO): ProductCommentDTO {
            return ProductCommentDTO(
                    productCommentId = productCommentVO.productCommentId,
                    productId = productCommentVO.productId,
                    memberId = productCommentVO.memberId,
                    content = productCommentVO.content
            )
        }
    }
}