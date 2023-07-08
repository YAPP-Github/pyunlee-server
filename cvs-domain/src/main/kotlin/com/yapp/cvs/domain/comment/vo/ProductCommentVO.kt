package com.yapp.cvs.domain.comment.vo

import com.yapp.cvs.domain.comment.entity.ProductComment

data class ProductCommentVO(
        val productCommentId: Long,
        val productId: Long,
        val memberId: Long,
        val content: String
) {
    companion object {
        fun from(productComment: ProductComment): ProductCommentVO {
            return ProductCommentVO(
                    productCommentId = productComment.productCommentId!!,
                    productId = productComment.productId,
                    memberId = productComment.memberId,
                    content = productComment.content
            )
        }
    }
}