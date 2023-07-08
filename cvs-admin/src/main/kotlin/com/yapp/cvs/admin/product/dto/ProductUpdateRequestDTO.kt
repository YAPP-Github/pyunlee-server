package com.yapp.cvs.admin.product.dto

import com.yapp.cvs.domain.enums.ProductCategoryType
import com.yapp.cvs.domain.product.vo.ProductUpdateVO
import com.yapp.cvs.domain.product.vo.ProductVO

class ProductUpdateRequestDTO(
    val brandName: String,
    val productName: String,
    val price: Long,
    val productCategoryType: ProductCategoryType,
    val isPbProduct: Boolean,
) {
    fun toVO(): ProductUpdateVO {
        return ProductUpdateVO(
            brandName = brandName,
            productName = productName,
            price = price,
            productCategoryType = productCategoryType,
            isPbProduct = isPbProduct
        )
    }
}