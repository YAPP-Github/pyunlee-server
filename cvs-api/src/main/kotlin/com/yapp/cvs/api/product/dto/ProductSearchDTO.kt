package com.yapp.cvs.api.product.dto

import com.yapp.cvs.domain.enums.ProductCategoryType
import com.yapp.cvs.domain.enums.RetailerType
import com.yapp.cvs.domain.product.entity.ProductOrderType
import com.yapp.cvs.domain.product.entity.ProductPromotionType
import com.yapp.cvs.domain.product.vo.ProductSearchVO
import java.time.LocalDateTime

data class ProductSearchDTO(
    val minPrice: Long? = null,
    val maxPrice: Long? = null,
    val productCategoryTypeList: List<ProductCategoryType> = listOf(),
    val pbOnly: Boolean? = null,
    val promotionTypeList: List<ProductPromotionType> = listOf(),
    val promotionRetailerList: List<RetailerType> = listOf(),
    val keyword: String? = null,
    val pageSize: Long = 10,
    val offsetProductId: Long? = null,
    val orderBy: ProductOrderType = ProductOrderType.RECENT,
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
            keyWord = keyword,
            pageSize = pageSize,
            offsetProductId = offsetProductId,
            orderBy = orderBy,
        )
    }
}