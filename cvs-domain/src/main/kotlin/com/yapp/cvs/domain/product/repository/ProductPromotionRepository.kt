package com.yapp.cvs.domain.product.repository

import com.yapp.cvs.domain.product.entity.ProductPromotion
import org.springframework.data.jpa.repository.JpaRepository

interface ProductPromotionRepository : JpaRepository<ProductPromotion, Long> {
    fun findAllByProductId(productId: Long): List<ProductPromotion>
}
