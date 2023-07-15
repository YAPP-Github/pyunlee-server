package com.yapp.cvs.domain.comment.repository

import com.yapp.cvs.domain.comment.entity.ProductCommentLikeSummary
import org.springframework.data.jpa.repository.JpaRepository

interface ProductCommentLikeSummaryRepository : JpaRepository<ProductCommentLikeSummary, Long> {
    fun findByProductIdAndMemberId(productId: Long, memberId: Long): ProductCommentLikeSummary?
}