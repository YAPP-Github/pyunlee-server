package com.yapp.cvs.domain.product.vo

import com.yapp.cvs.domain.enums.ProductCategoryType
import com.yapp.cvs.domain.product.entity.Product

data class ProductDetailVO(
    val productId: Long,
    val brandName: String,
    val productName: String,
    val price: Long,
    val productCategoryType: ProductCategoryType,
    val isPbProduct: Boolean,
    val imageUrl: String?,
    val productPromotionVOList: List<ProductPromotionVO>,
    val productScoreVO: ProductScoreVO?
) {
    companion object {
        fun from(product: Product): ProductDetailVO {
            return ProductDetailVO(
                productId = product.productId!!,
                brandName = product.brandName,
                productName = product.productName,
                price = product.price,
                productCategoryType = product.productCategoryType,
                isPbProduct = product.pbProductMappingList.isEmpty().not(),
                productPromotionVOList = product.productPromotionList.map { ProductPromotionVO.from(it) },
                imageUrl = product.imageUrl,
                productScoreVO = product.productLikeSummaryList.firstOrNull()?.let { ProductScoreVO.from(it) }
            )
        }
    }
}