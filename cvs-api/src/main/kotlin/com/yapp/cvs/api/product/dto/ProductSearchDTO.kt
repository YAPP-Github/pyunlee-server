package com.yapp.cvs.api.product.dto

import com.yapp.cvs.domain.enums.ProductCategoryType
import com.yapp.cvs.domain.enums.RetailerType
import com.yapp.cvs.domain.product.entity.ProductPromotionType
import com.yapp.cvs.domain.product.vo.ProductSearchVO
import java.time.LocalDateTime

data class ProductSearchDTO(
    val minPrice: Long?,
    val maxPrice: Long?,
    val productCategoryTypeList: List<ProductCategoryType> = listOf(),
    val pbOnly: Boolean?,
    val promotionTypeList: List<ProductPromotionType> = listOf(),
    val promotionRetailerList: List<RetailerType> = listOf(),
    val pageSize: Long = 10,
    val offsetProductId: Long?
) {
    fun toVO(): ProductSearchVO {
        return ProductSearchVO(
            minPrice = minPrice,
            maxPrice = maxPrice,
            productCategoryTypeList = productCategoryTypeList,
            pbOnly = pbOnly ?: false,
            promotionTypeList = promotionTypeList,
            promotionRetailerList = promotionRetailerList,
            appliedDateTime = LocalDateTime.now(),
            pageSize = pageSize,
            offsetProductId = offsetProductId
        )
    }
}