package com.yapp.cvs.domain.product.vo

import com.yapp.cvs.domain.enums.ProductCategoryType
import com.yapp.cvs.domain.product.entity.ProductPromotion

data class ProductDetailVO(
    val productId: Long,
    val brandName: String,
    val productName: String,
    val price: Long,
    val productCategoryType: ProductCategoryType,
    val isPbProduct: Boolean,
    val imageUrl: String,
    val productPromotionVOList: List<ProductPromotionVO>,
    val productScoreVO: ProductScoreVO
) {
    companion object {
        fun of(
            productPbVO: ProductPbVO,
            productPromotionList: List<ProductPromotion>
        ): ProductDetailVO {
            return ProductDetailVO(
                productId = productPbVO.productId,
                brandName = productPbVO.brandName,
                productName = productPbVO.productName,
                price = productPbVO.price,
                productCategoryType = productPbVO.productCategoryType,
                isPbProduct = productPbVO.isPbProduct,
                imageUrl = productPbVO.imageUrl,
                productPromotionVOList = productPromotionList.map { ProductPromotionVO.from(it) },
                productScoreVO = ProductScoreVO.tempEmpty()
            )
        }
    }
}