package com.yapp.cvs.domain.comment.repository

import com.yapp.cvs.domain.comment.entity.ProductComment
import com.yapp.cvs.domain.comment.vo.ProductCommentDetailVO
import com.yapp.cvs.domain.comment.vo.ProductCommentSearchVO
import org.springframework.data.jpa.repository.JpaRepository

interface ProductCommentRepository : JpaRepository<ProductComment, Long>, ProductCommentCustom {
    fun findByProductIdAndMemberIdAndValidTrue(productId: Long, memberId: Long): ProductComment?
    fun existsByProductIdAndMemberIdAndValidTrue(productId: Long, memberId: Long): Boolean
    fun deleteByProductIdAndMemberId(productId: Long, memberId: Long)
}

interface ProductCommentCustom {
    fun findLatestByProductIdAndMemberId(productId: Long, memberId: Long): ProductComment?
    fun findRecentComments(): List<ProductCommentDetailVO>
    fun findAllByProductIdAndPageOffset(productId: Long, productCommentSearchVO: ProductCommentSearchVO): List<ProductCommentDetailVO>
}
