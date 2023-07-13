package com.yapp.cvs.domain.like.vo

import com.yapp.cvs.domain.like.entity.ProductLikeSummary

data class ProductLikeSummaryVO(
    val productId: Long,
    val likeCount: Long,
    val dislikeCount: Long,
    val totalCount: Long
) {
    companion object {
        fun from(productLikeSummary: ProductLikeSummary): ProductLikeSummaryVO {
            return ProductLikeSummaryVO(
                productId = productLikeSummary.productId,
                likeCount = productLikeSummary.likeCount,
                dislikeCount = productLikeSummary.getDislikeCount(),
                totalCount = productLikeSummary.totalCount
            )
        }
    }
}