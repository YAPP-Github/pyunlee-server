package com.yapp.cvs.domain.product.vo

import com.yapp.cvs.domain.enums.RetailerType
import com.yapp.cvs.domain.product.entity.ProductPromotion
import com.yapp.cvs.domain.product.entity.ProductPromotionType

data class ProductPromotionVO(
    val productId: Long,
    val retailerType: RetailerType,
    val promotionType: ProductPromotionType
) {
    companion object {
        fun from(productPromotion: ProductPromotion): ProductPromotionVO {
            return ProductPromotionVO(
                productId = productPromotion.productId,
                retailerType = productPromotion.retailerType,
                promotionType = productPromotion.promotionType
            )
        }
    }
}