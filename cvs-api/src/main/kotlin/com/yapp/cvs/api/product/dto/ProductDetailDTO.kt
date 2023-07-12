package com.yapp.cvs.api.product.dto

import com.yapp.cvs.domain.product.vo.ProductDetailVO

data class ProductDetailDTO(
    val productId: Long,
    val brandName: String,
    val name: String,
    val price: Long,
    val categoryType: String,
    val isPbProduct: Boolean,
    val imageUrl: String?,
    val promotionList: List<ProductPromotionDTO>,
    val score: ProductScoreDTO?
) {
    companion object {
        fun from(productDetailVO: ProductDetailVO): ProductDetailDTO {
            return ProductDetailDTO(
                productId = productDetailVO.productId,
                brandName = productDetailVO.brandName,
                name = productDetailVO.productName,
                price = productDetailVO.price,
                categoryType = productDetailVO.productCategoryType.name,
                isPbProduct = productDetailVO.isPbProduct,
                imageUrl = productDetailVO.imageUrl,
                promotionList = productDetailVO.productPromotionVOList.map { ProductPromotionDTO.from(it) },
                score = productDetailVO.productScoreVO?.let { ProductScoreDTO.from(it) }
            )
        }
    }
}