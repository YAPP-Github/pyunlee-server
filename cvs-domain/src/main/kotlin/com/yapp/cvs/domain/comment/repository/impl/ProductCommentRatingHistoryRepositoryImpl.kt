package com.yapp.cvs.domain.comment.repository.impl

import com.yapp.cvs.domain.comment.entity.ProductCommentRatingHistory
import com.yapp.cvs.domain.comment.repository.ProductCommentRatingHistoryRepositoryCustom
import org.springframework.stereotype.Repository

@Repository
class ProductCommentRatingHistoryRepositoryImpl: ProductCommentRatingHistoryRepositoryCustom {
    override fun findLatestMemberRatingOnProductComment(memberId: Long, productCommentId: Long): ProductCommentRatingHistory? {
        TODO("Not yet implemented")
    }
}