package com.yapp.cvs.domain.product.vo

import com.yapp.cvs.domain.like.entity.ProductLikeSummary

class ProductScoreVO(
    val totalCount: Long,
    val likeCount: Long,
    val dislikeCount: Long,
    val likeRatio: Int,
    val dislikeRatio: Int
) {
    companion object {
        fun from(productLikeSummary: ProductLikeSummary): ProductScoreVO {
            return ProductScoreVO(
                totalCount = productLikeSummary.totalCount,
                likeCount = productLikeSummary.likeCount,
                dislikeCount = productLikeSummary.getDislikeCount(),
                likeRatio = productLikeSummary.getLikeRatio(),
                dislikeRatio = productLikeSummary.getDislikeRatio()
            )
        }
    }
}