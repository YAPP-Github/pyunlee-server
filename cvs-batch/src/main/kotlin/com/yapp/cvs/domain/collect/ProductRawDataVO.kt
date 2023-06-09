package com.yapp.cvs.domain.collect

import com.yapp.cvs.domains.enums.ProductCategoryType
import com.yapp.cvs.domains.enums.RetailerType
import com.yapp.cvs.domains.product.entity.ProductEntity

data class ProductRawDataVO(
    val name: String,
    val brandName: String,
    val price: Long,
    val categoryType: ProductCategoryType,
    val barcode: String,
    val imageUrl: String,
    val retailerType: RetailerType,
    val isPbProduct: Boolean = false,
) {
    fun to(): ProductEntity {
        return ProductEntity(
            productName = name,
            brandName = brandName,
            price = price,
            productCategoryType = categoryType,
            barcode = barcode,
            imageUrl = imageUrl,
        )
    }
}
