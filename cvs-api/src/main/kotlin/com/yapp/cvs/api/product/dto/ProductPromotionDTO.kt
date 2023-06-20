package com.yapp.cvs.api.product.dto

import com.yapp.cvs.domain.product.vo.ProductPromotionVO

data class ProductPromotionDTO(
    val productId: Long,
    val retailerType: String,
    val promotionType: String
) {
    companion object {
        fun from(productPromotionVO: ProductPromotionVO): ProductPromotionDTO {
            return ProductPromotionDTO(
                productId = productPromotionVO.productId,
                retailerType = productPromotionVO.retailerType.name,
                promotionType = productPromotionVO.promotionType.name
            )
        }
    }
}