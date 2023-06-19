package com.yapp.cvs.api.product.dto

import com.yapp.cvs.domain.product.vo.ProductScoreVO

data class ProductScoreDTO(
    val totalCount: Long,
    val likeCount: Long,
    val dislikeCount: Long,
    val likeRatio: Int,
    val dislikeRatio: Int
) {
    companion object {
        fun from(productScoreVO: ProductScoreVO): ProductScoreDTO {
            return ProductScoreDTO(
                totalCount = productScoreVO.totalCount,
                likeCount = productScoreVO.likeCount,
                dislikeCount = productScoreVO.dislikeCount,
                likeRatio = productScoreVO.likeRatio,
                dislikeRatio = productScoreVO.dislikeRatio
            )
        }
    }
}