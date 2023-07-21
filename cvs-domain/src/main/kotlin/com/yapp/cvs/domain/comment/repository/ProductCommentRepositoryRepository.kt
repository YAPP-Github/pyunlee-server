package com.yapp.cvs.domain.comment.repository

import com.yapp.cvs.domain.comment.entity.ProductComment
import com.yapp.cvs.domain.comment.view.ProductCommentDetailView
import com.yapp.cvs.domain.comment.view.ProductCommentView
import com.yapp.cvs.domain.comment.vo.ProductCommentDetailVO
import com.yapp.cvs.domain.comment.vo.ProductCommentSearchVO
import com.yapp.cvs.domain.comment.vo.ProductCommentVO
import org.springframework.data.jpa.repository.JpaRepository

interface ProductCommentRepositoryRepository : JpaRepository<ProductComment, Long>, ProductCommentRepositoryCustom {
    fun findByProductCommentIdAndValidTrue(commentId: Long): ProductComment?
    fun existsByProductIdAndMemberIdAndValidTrue(productId: Long, memberId: Long): Boolean

    fun countByProductIdAndValid(productId: Long, valid: Boolean): Long
    fun countByMemberIdAndValid(memberId: Long, valid: Boolean): Long
}

interface ProductCommentRepositoryCustom {
    fun findLatestById(commentId: Long): ProductComment?
    fun findLatestByProductIdAndMemberId(productId: Long, memberId: Long): ProductComment?
    fun findByProductIdAndSearchCondition(productId: Long, productCommentSearchVO: ProductCommentSearchVO): List<ProductCommentView>
    fun findRecentCommentList(size: Int): List<ProductCommentDetailView>
}
