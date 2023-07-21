package com.yapp.cvs.domain.comment.application

import com.yapp.cvs.domain.comment.entity.ProductCommentRatingHistory
import com.yapp.cvs.domain.comment.repository.ProductCommentRatingHistoryRepository
import org.springframework.stereotype.Service

@Service
class ProductCommentRatingHistoryService(
    private val productCommentRatingHistoryRepository: ProductCommentRatingHistoryRepository
) {
    fun save(productCommentRatingHistory: ProductCommentRatingHistory) {
        productCommentRatingHistoryRepository.save(productCommentRatingHistory)
    }

    fun findProductCommentRatingHistoryOrNull(memberId:Long, productCommentId: Long): ProductCommentRatingHistory? {
        return productCommentRatingHistoryRepository.findLatestMemberRatingOnProductComment(memberId, productCommentId)
    }

    fun findAllProductCommentRatingHistoryByMember(memberId: Long): List<ProductCommentRatingHistory> {
        return productCommentRatingHistoryRepository.findAllByMemberIdAndValid(memberId, true)
    }
    fun like(memberId:Long, productCommentId: Long) {
        productCommentRatingHistoryRepository.save(ProductCommentRatingHistory.like(memberId, productCommentId))
    }

    fun cancel(memberId:Long, productCommentId: Long) {
        productCommentRatingHistoryRepository.findLatestMemberRatingOnProductComment(memberId, productCommentId)
            ?.apply { valid = false }
            ?.let { productCommentRatingHistoryRepository.save(it) }
    }
}