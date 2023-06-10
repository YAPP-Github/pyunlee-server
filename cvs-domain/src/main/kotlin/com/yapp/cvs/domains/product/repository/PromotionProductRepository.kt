package com.yapp.cvs.domains.product.repository

import com.yapp.cvs.domains.product.entity.PromotionProduct
import org.springframework.data.jpa.repository.JpaRepository

interface PromotionProductRepository: JpaRepository<PromotionProduct, Long> {
}