package com.yapp.cvs.api.product.dto

import com.yapp.cvs.domain.enums.ProductLikeType
import com.yapp.cvs.domain.like.vo.ProductLikeHistoryVO

data class ProductLikeHistoryDTO(
        val productId: Long,
        val memberId: Long,
        val likeType: ProductLikeType
) {
    companion object {
        fun from(productLikeHistoryVO: ProductLikeHistoryVO): ProductLikeHistoryDTO {
            return ProductLikeHistoryDTO(
                    productId = productLikeHistoryVO.productId,
                    memberId = productLikeHistoryVO.memberId,
                    likeType = productLikeHistoryVO.likeType
            )
        }
    }
}