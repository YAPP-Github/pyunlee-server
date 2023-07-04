package com.yapp.cvs.domain.comment.repository

import com.yapp.cvs.domain.comment.entity.ProductComment
import org.springframework.data.jpa.repository.JpaRepository

interface ProductCommentRepository : JpaRepository<ProductComment, Long> {
    fun findByProductIdAndMemberId(productId: Long, memberId: Long): ProductComment?
    fun deleteByProductIdAndMemberId(productId: Long, memberId: Long)
}