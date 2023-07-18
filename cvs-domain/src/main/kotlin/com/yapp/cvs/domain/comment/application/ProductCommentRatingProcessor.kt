package com.yapp.cvs.domain.comment.application

import com.yapp.cvs.domain.comment.entity.ProductCommentRatingHistory
import com.yapp.cvs.domain.enums.DistributedLockType
import com.yapp.cvs.exception.BadRequestException
import com.yapp.cvs.infrastructure.redis.lock.DistributedLock
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ProductCommentRatingProcessor(
    private val productCommentService: ProductCommentService,
    private val productCommentRatingHistoryService: ProductCommentRatingHistoryService,
    private val productCommentRatingSummaryService: ProductCommentRatingSummaryService
) {
    @DistributedLock(DistributedLockType.COMMENT_LIKE, ["memberId", "productCommentId"])
    fun likeComment(memberId: Long, productCommentId: Long) {
        val productComment = productCommentService.findProductComment(productCommentId)

        val latestRating = productCommentRatingHistoryService.findProductCommentRatingHistoryOrNull(memberId, productComment.productCommentId!!)

        if (latestRating != null) {
            throw BadRequestException("이미 좋아요 한 코멘트 입니다")
        }

        val productCommentRatingSummary = productCommentRatingSummaryService.findByProductCommentIdOrDefault(productComment.productCommentId!!)

        productCommentRatingSummary.like()
        productCommentRatingSummaryService.save(productCommentRatingSummary)
        productCommentRatingHistoryService.save(ProductCommentRatingHistory.like(memberId, productComment.productCommentId!!))
    }

    @DistributedLock(DistributedLockType.COMMENT_LIKE, ["memberId", "productCommentId"])
    fun cancelLikeComment(memberId: Long, productCommentId: Long) {
        val productComment = productCommentService.findProductComment(productCommentId)

        val latestRating = productCommentRatingHistoryService.findProductCommentRatingHistoryOrNull(memberId, productComment.productCommentId!!)
            ?.apply { valid = false }
            ?.let { productCommentRatingHistoryService.save(it) }

        val productCommentRatingSummary = productCommentRatingSummaryService.findByProductCommentId(productComment.productCommentId!!)

        productCommentRatingSummary.cancelLike()
        productCommentRatingSummaryService.save(productCommentRatingSummary)
    }

}
