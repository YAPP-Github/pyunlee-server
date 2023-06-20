package com.yapp.cvs.domain.product.vo

class ProductScoreVO(
    val totalCount: Long,
    val likeCount: Long,
    val dislikeCount: Long,
    val likeRatio: Int,
    val dislikeRatio: Int
) {
    companion object {
        fun tempEmpty(): ProductScoreVO {
            return ProductScoreVO(
                totalCount = 100,
                likeCount = 82,
                dislikeCount = 18,
                likeRatio = 82,
                dislikeRatio = 18
            )
        }
    }
}