package com.yapp.cvs.domain.like.vo

import com.yapp.cvs.domain.enums.ProductLikeType
import com.yapp.cvs.domain.like.entity.ProductLikeHistory

class ProductLikeVO(
        val productId: Long,
        val likeType: ProductLikeType = ProductLikeType.NONE,
) {
    companion object {
        fun from(productLikeHistory: ProductLikeHistory): ProductLikeVO {
            return ProductLikeVO(
                    productId = productLikeHistory.productId,
                    likeType = productLikeHistory.likeType
            )
        }

        fun noneOf(productId: Long): ProductLikeVO {
            return ProductLikeVO(productId = productId)
        }
    }
}