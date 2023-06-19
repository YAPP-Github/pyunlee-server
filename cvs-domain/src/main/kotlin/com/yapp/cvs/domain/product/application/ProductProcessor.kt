package com.yapp.cvs.domain.product.application

import com.yapp.cvs.domain.product.vo.ProductDetailVO
import org.springframework.stereotype.Service

@Service
class ProductProcessor(
    private val productService: ProductService,
    private val productPromotionService: ProductPromotionService,
) {
    fun getProductDetail(productId: Long): ProductDetailVO {
        val productPbVO = productService.findProductPbInfo(productId)
        val productPromotionList = productPromotionService.findProductPromotionList(productId)

        productService.increaseProductViewCount(productId)

        return ProductDetailVO.of(productPbVO, productPromotionList)
    }
}