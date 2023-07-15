package com.yapp.cvs.domain.comment.vo

import com.yapp.cvs.domain.comment.entity.ProductCommentLikeSummary

data class ProductCommentLikeSummaryVO(
        val productId: Long,
        val memberId: Long,
        val likeCount: Long
) {
    companion object {
        fun from(productCommentLikeSummary: ProductCommentLikeSummary): ProductCommentLikeSummaryVO {
            return ProductCommentLikeSummaryVO(
                    productId = productCommentLikeSummary.productId,
                    memberId = productCommentLikeSummary.memberId,
                    likeCount = productCommentLikeSummary.likeCount
            )
        }
    }
}