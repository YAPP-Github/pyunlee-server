package com.yapp.cvs.domain.comment.repository

import com.yapp.cvs.domain.comment.entity.ProductCommentHistory
import com.yapp.cvs.domain.comment.vo.ProductCommentDetailVO
import com.yapp.cvs.domain.comment.vo.ProductCommentSearchVO
import org.springframework.data.jpa.repository.JpaRepository

interface ProductCommentHistoryRepository : JpaRepository<ProductCommentHistory, Long>, ProductCommentHistoryCustom

interface ProductCommentHistoryCustom {
    fun findAllByProductIdAndPageOffset(productId: Long, productCommentSearchVO: ProductCommentSearchVO): List<ProductCommentDetailVO>
}
