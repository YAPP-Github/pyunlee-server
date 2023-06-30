package com.yapp.cvs.domain.collect

import com.yapp.cvs.domain.enums.ProductCategoryType
import com.yapp.cvs.domain.enums.RetailerType
import com.yapp.cvs.domain.product.entity.Product

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
    fun to(s3ImageUrl: String?): Product {
        return Product(
            productName = name,
            brandName = brandName,
            price = price,
            productCategoryType = categoryType,
            barcode = barcode,
            imageUrl = s3ImageUrl,
        )
    }
}
