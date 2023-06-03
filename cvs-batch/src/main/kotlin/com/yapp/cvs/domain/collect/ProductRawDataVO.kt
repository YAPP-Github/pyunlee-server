package com.yapp.cvs.domain.collect

import com.yapp.cvs.domains.enums.ProductCategoryType
import com.yapp.cvs.domains.enums.RetailerType

data class ProductRawDataVO(
    val name: String,
    val brandName: String,
    val price: Long,
    val categoryType: ProductCategoryType,
    val barcode: String,
    val imageUrl: String,
    val retailerType: RetailerType,
    val isPbProduct: Boolean = false
) {
}