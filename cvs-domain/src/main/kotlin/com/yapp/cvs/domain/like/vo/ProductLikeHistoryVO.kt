package com.yapp.cvs.domain.like.vo

import com.yapp.cvs.domain.enums.ProductLikeType
import com.yapp.cvs.domain.like.entity.ProductLikeHistory
import com.yapp.cvs.domain.like.entity.ProductLikeSummary

class ProductLikeHistoryVO(
        val productId: Long,
        val memberId: Long,
        val likeType: ProductLikeType
) {
    companion object {
        fun from(productLikeHistory: ProductLikeHistory): ProductLikeHistoryVO {
            return ProductLikeHistoryVO(
                    productId = productLikeHistory.productId,
                    memberId = productLikeHistory.memberId,
                    likeType = productLikeHistory.likeType,
            )
        }
    }
}