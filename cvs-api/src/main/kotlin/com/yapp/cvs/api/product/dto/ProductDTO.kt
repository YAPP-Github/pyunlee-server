package com.yapp.cvs.api.product.dto

import com.yapp.cvs.domain.product.vo.ProductVO

data class ProductDTO(
    val productId: Long,
    val brandName: String,
    val productName: String,
    val price: Long,
    val productCategoryType: String,
    val isPbProduct: Boolean,
    val isPromotion: Boolean,
    val imageUrl: String,
) {
    companion object {
        fun from(productVO: ProductVO): ProductDTO {
            return ProductDTO(
                productId = productVO.productId,
                brandName = productVO.brandName,
                productName = productVO.productName,
                price = productVO.price,
                productCategoryType = productVO.productCategoryType.displayName,
                isPbProduct = productVO.isPbProduct,
                isPromotion = productVO.isPromotion,
                imageUrl = productVO.imageUrl
            )
        }
    }
}