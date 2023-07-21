package com.yapp.cvs.domain.comment.repository

import com.yapp.cvs.domain.comment.entity.ProductCommentRatingHistory
import org.springframework.data.jpa.repository.JpaRepository

interface ProductCommentRatingHistoryRepository: JpaRepository<ProductCommentRatingHistory, Long>, ProductCommentRatingHistoryRepositoryCustom {
    fun findAllByMemberIdAndValid(memberId: Long, valid: Boolean): List<ProductCommentRatingHistory>
}

interface ProductCommentRatingHistoryRepositoryCustom {
    fun findLatestMemberRatingOnProductComment(memberId: Long, productCommentId: Long): ProductCommentRatingHistory?
}