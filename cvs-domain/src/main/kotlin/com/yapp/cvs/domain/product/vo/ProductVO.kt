package com.yapp.cvs.domain.product.vo

import com.yapp.cvs.domain.enums.ProductCategoryType
import com.yapp.cvs.domain.product.entity.Product

data class ProductVO(
    val productId: Long,
    val brandName: String,
    val productName: String,
    val price: Long,
    val productCategoryType: ProductCategoryType,
    val isPbProduct: Boolean,
    val isPromotion: Boolean,
    val imageUrl: String?,
) {
    companion object {
        fun from(product: Product): ProductVO {
            return ProductVO(
                productId = product.productId!!,
                brandName = product.brandName,
                productName = product.productName,
                price = product.price,
                productCategoryType = product.productCategoryType,
                isPbProduct = product.pbProductMappingList.isEmpty().not(),
                isPromotion = product.productPromotionList.isEmpty().not(),
                imageUrl = product.imageUrl
            )
        }
    }
}