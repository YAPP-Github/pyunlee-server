package com.yapp.cvs.domain.like.repository

import com.yapp.cvs.domain.like.entity.ProductLikeHistory
import org.springframework.data.jpa.repository.JpaRepository

interface ProductLikeHistoryRepository
    : JpaRepository<ProductLikeHistory, Long>, ProductLikeHistoryCustom

interface ProductLikeHistoryCustom {
    fun findLatestByProductIdAndMemberId(productId: Long, memberId: Long): ProductLikeHistory?
}
