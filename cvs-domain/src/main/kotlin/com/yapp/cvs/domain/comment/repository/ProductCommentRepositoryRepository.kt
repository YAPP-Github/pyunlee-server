package com.yapp.cvs.domain.comment.repository

import com.yapp.cvs.domain.comment.entity.ProductComment
import com.yapp.cvs.domain.comment.vo.ProductCommentDetailVO
import com.yapp.cvs.domain.comment.vo.ProductCommentSearchVO
import org.springframework.data.jpa.repository.JpaRepository

interface ProductCommentRepositoryRepository : JpaRepository<ProductComment, Long>, ProductCommentRepositoryCustom {
    fun findByProductCommentIdAndValidTrue(commentId: Long): ProductComment?
    fun existsByProductIdAndMemberIdAndValidTrue(productId: Long, memberId: Long): Boolean
}

interface ProductCommentRepositoryCustom {
    fun findLatestById(commentId: Long): ProductComment?
    fun findLatestByProductIdAndMemberId(productId: Long, memberId: Long): ProductComment?
    fun findAllByCondition(productCommentSearchVO: ProductCommentSearchVO): List<ProductCommentDetailVO>

    fun findRecentCommentList(size: Int): List<ProductComment>
}
