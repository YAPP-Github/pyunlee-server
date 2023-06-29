package com.yapp.cvs.domain.like.repository

import com.yapp.cvs.domain.like.entity.ProductLikeHistory
import com.yapp.cvs.domain.like.entity.ProductLikeSummary
import org.springframework.data.jpa.repository.JpaRepository

interface ProductLikeSummaryRepository
    : JpaRepository<ProductLikeSummary, Long> {
        fun findByProductId(productId: Long): ProductLikeSummary?
    }