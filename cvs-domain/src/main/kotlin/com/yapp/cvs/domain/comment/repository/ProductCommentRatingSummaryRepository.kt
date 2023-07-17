package com.yapp.cvs.domain.comment.repository

import com.yapp.cvs.domain.comment.entity.ProductCommentRatingSummary
import org.springframework.data.jpa.repository.JpaRepository

interface ProductCommentRatingSummaryRepository : JpaRepository<ProductCommentRatingSummary, Long> {
    fun findByProductCommentId(productCommentId: Long): ProductCommentRatingSummary?
}