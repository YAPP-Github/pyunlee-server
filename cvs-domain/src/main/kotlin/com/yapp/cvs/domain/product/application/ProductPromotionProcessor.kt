package com.yapp.cvs.domain.product.application

import org.springframework.stereotype.Service

@Service
class ProductPromotionProcessor(
    private val productPromotionService: ProductPromotionService
) {
}