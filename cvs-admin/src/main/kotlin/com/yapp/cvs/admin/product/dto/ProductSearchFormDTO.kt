package com.yapp.cvs.admin.product.dto

import com.yapp.cvs.domain.base.dto.PageSearchFormDTO
import com.yapp.cvs.domain.base.vo.PageSearchVO
import com.yapp.cvs.domain.enums.ProductCategoryType
import com.yapp.cvs.domain.enums.RetailerType
import com.yapp.cvs.domain.product.entity.ProductOrderType
import com.yapp.cvs.domain.product.entity.ProductPromotionType
import com.yapp.cvs.domain.product.vo.ProductSearchVO
import java.time.LocalDateTime

class ProductSearchFormDTO(
    val minPrice: Long? = null,
    val maxPrice: Long? = null,
    val productCategoryTypeList: List<ProductCategoryType> = listOf(),
    val pbOnly: Boolean? = null,
    val promotionTypeList: List<ProductPromotionType> = listOf(),
    val promotionRetailerList: List<RetailerType> = listOf(),
    val keyword: String? = null,
    val orderBy: ProductOrderType = ProductOrderType.RECENT,
    override val pageNum: Int = 0
): PageSearchFormDTO() {
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
            orderBy = orderBy,
        )
    }
    fun toPageSearchVO(): PageSearchVO {
        return PageSearchVO(
            pageNum = pageNum,
            pageSize = pageSize
        )
    }
}