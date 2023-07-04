package com.yapp.cvs.domain.comment.vo

import com.yapp.cvs.domain.comment.entity.ProductCommentHistory

data class ProductCommentHistoryVO(
        val productCommentHistoryId: Long,
        val productId: Long,
        val memberId: Long,
        val content: String
) {
    companion object {
        fun from(productCommentHistory: ProductCommentHistory): ProductCommentHistoryVO {
            return ProductCommentHistoryVO(
                    productCommentHistoryId = productCommentHistory.productCommentHistoryId!!,
                    productId = productCommentHistory.productId,
                    memberId = productCommentHistory.memberId,
                    content = productCommentHistory.content
            )
        }
    }
}