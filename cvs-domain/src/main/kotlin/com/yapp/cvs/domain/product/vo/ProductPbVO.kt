package com.yapp.cvs.domain.product.vo

import com.yapp.cvs.domain.enums.ProductCategoryType

data class ProductPbVO(
    val productId: Long,
    val brandName: String,
    val productName: String,
    val price: Long,
    val productCategoryType: ProductCategoryType,
    val isPbProduct: Boolean,
    val imageUrl: String
) {
}