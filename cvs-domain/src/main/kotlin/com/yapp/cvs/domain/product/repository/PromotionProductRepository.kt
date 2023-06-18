package com.yapp.cvs.domain.product.repository

import com.yapp.cvs.domain.product.entity.PromotionProduct
import org.springframework.data.jpa.repository.JpaRepository

interface PromotionProductRepository : JpaRepository<PromotionProduct, Long>
