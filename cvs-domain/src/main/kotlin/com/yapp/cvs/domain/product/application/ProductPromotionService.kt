package com.yapp.cvs.domain.product.application

import com.yapp.cvs.domain.product.entity.ProductPromotion
import com.yapp.cvs.domain.product.repository.ProductPromotionRepository
import org.springframework.stereotype.Service

@Service
class ProductPromotionService(
    private val productProductPromotionRepository: ProductPromotionRepository
) {
    fun findProductPromotionList(productId: Long): List<ProductPromotion> {
        return productProductPromotionRepository.findAllByProductId(productId)
    }
}