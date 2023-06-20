package com.yapp.cvs.domain.like.repository

import com.yapp.cvs.domain.enums.ProductLikeType
import com.yapp.cvs.domain.like.entity.ProductLikeHistory
import com.yapp.cvs.domain.like.vo.ProductLikeVO
import org.springframework.data.jpa.repository.JpaRepository

interface ProductLikeHistoryRepository
    : JpaRepository<ProductLikeHistory, Long>, ProductLikeHistoryRepositoryCustom

interface ProductLikeHistoryRepositoryCustom {
    fun findLatestByProductIdAndMemberId(productId: Long, memberId: Long): ProductLikeVO?
    fun existsLatestByProductIdAndMemberIdAndType(productId: Long, memberId: Long, type: ProductLikeType): Boolean
}
