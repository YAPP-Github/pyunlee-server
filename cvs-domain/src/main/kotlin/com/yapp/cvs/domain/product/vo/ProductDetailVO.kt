package com.yapp.cvs.domain.product.vo

import com.yapp.cvs.domain.enums.ProductCategoryType
import com.yapp.cvs.domain.enums.ProductLikeType
import com.yapp.cvs.domain.like.entity.MemberProductLikeMapping
import com.yapp.cvs.domain.product.entity.Product

data class ProductDetailVO(
    val productId: Long,
    val brandName: String,
    val productName: String,
    val price: Long,
    val productCategoryType: ProductCategoryType,
    val isPbProduct: Boolean,
    val productRatingType: ProductLikeType,
    val imageUrl: String?,
    val productPromotionVOList: List<ProductPromotionVO>,
    val productScoreVO: ProductScoreVO?
) {
    companion object {
        fun from(product: Product, memberProductMapping: MemberProductLikeMapping?): ProductDetailVO {
            return ProductDetailVO(
                productId = product.productId!!,
                brandName = product.brandName,
                productName = product.productName,
                price = product.price,
                productCategoryType = product.productCategoryType,
                isPbProduct = product.pbProductMappingList.isEmpty().not(),
                productRatingType = memberProductMapping?.likeType ?: ProductLikeType.NONE,
                imageUrl = product.imageUrl,
                productPromotionVOList = product.productPromotionList.map { ProductPromotionVO.from(it) },
                productScoreVO = product.productLikeSummaryList.firstOrNull()?.let { ProductScoreVO.from(it) }
            )
        }
    }
}