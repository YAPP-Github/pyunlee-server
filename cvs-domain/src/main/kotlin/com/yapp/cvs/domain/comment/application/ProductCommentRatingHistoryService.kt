package com.yapp.cvs.domain.comment.application

import com.yapp.cvs.domain.comment.entity.ProductCommentRatingHistory
import com.yapp.cvs.domain.comment.repository.ProductCommentRatingHistoryRepository
import com.yapp.cvs.domain.comment.repository.ProductCommentRatingSummaryRepository
import org.springframework.stereotype.Service

@Service
class ProductCommentRatingHistoryService(
    private val productCommentRatingHistoryRepository: ProductCommentRatingHistoryRepository,
    private val productCommentRatingSummaryRepository: ProductCommentRatingSummaryRepository
) {
    fun save(productCommentRatingHistory: ProductCommentRatingHistory) {
        productCommentRatingHistoryRepository.save(productCommentRatingHistory)
    }

    fun findProductCommentRatingHistoryOrNull(memberId:Long, productCommentId: Long): ProductCommentRatingHistory? {
        return productCommentRatingHistoryRepository.findLatestMemberRatingOnProductComment(memberId, productCommentId)
    }
}