package com.yapp.cvs.domain.comment.repository.impl

import com.yapp.cvs.domain.comment.entity.ProductCommentRatingHistory
import com.yapp.cvs.domain.comment.entity.QProductCommentRatingHistory.productCommentRatingHistory
import com.yapp.cvs.domain.comment.repository.ProductCommentRatingHistoryRepositoryCustom
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class ProductCommentRatingHistoryRepositoryImpl: QuerydslRepositorySupport(ProductCommentRatingHistory::class.java), ProductCommentRatingHistoryRepositoryCustom {
    override fun findLatestMemberRatingOnProductComment(memberId: Long, productCommentId: Long): ProductCommentRatingHistory? {
        return from(productCommentRatingHistory)
            .where(productCommentRatingHistory.memberId.eq(memberId)
                .and(productCommentRatingHistory.productCommentId.eq(productCommentId))
                .and(productCommentRatingHistory.valid.eq(true)))
            .orderBy(productCommentRatingHistory.productCommentRatingHistoryId.desc())
            .fetchFirst()

    }
}