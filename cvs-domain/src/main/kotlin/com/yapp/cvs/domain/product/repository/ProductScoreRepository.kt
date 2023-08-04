package com.yapp.cvs.domain.product.repository

import com.yapp.cvs.domain.product.entity.ProductScore
import org.springframework.data.jpa.repository.JpaRepository

interface ProductScoreRepository: JpaRepository<ProductScore, Long> {
}