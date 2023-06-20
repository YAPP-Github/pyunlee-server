package com.yapp.cvs.domain.product.vo

import com.yapp.cvs.domain.enums.ProductCategoryType
import com.yapp.cvs.domain.enums.RetailerType
import com.yapp.cvs.domain.product.entity.ProductPromotionType
import java.time.LocalDateTime

data class ProductSearchVO(
    val minPrice: Long?,
    val maxPrice: Long?,
    val productCategoryTypeList: List<ProductCategoryType>,
    val pbOnly: Boolean,
    val promotionTypeList: List<ProductPromotionType>,
    val promotionRetailerList: List<RetailerType>,
    val appliedDateTime: LocalDateTime,
    val pageSize: Long,
    val offsetProductId: Long?
) {
}