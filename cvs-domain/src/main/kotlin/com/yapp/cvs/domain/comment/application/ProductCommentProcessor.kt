package com.yapp.cvs.domain.comment.application

import com.yapp.cvs.domain.base.vo.OffsetPageVO
import com.yapp.cvs.domain.comment.vo.ProductCommentDetailVO
import com.yapp.cvs.domain.comment.vo.ProductCommentSearchVO
import com.yapp.cvs.domain.comment.vo.ProductCommentHistoryVO
import com.yapp.cvs.domain.enums.DistributedLockType
import com.yapp.cvs.domain.enums.ProductLikeType
import com.yapp.cvs.domain.like.application.ProductLikeHistoryService
import com.yapp.cvs.domain.like.application.ProductLikeSummaryService
import com.yapp.cvs.infrastructure.redis.lock.DistributedLock
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ProductCommentProcessor(
        private val productCommentService: ProductCommentHistoryService,
        private val productCommentSummaryService: ProductCommentSummaryService,
        private val productLikeHistoryService: ProductLikeHistoryService,
        private val productLikeSummaryService: ProductLikeSummaryService

) {
    fun getCommentDetails(productId: Long, memberId: Long, productCommentSearchVO: ProductCommentSearchVO): OffsetPageVO<ProductCommentDetailVO> {
        val result = productCommentService.findProductCommentsPage(productId, productCommentSearchVO)
        result.filter { it.memberId == memberId }
                .forEach { it.isOwner = true }
        return OffsetPageVO(result.lastOrNull()?.productCommentId, result)
    }

    @DistributedLock(DistributedLockType.COMMENT, ["productId", "memberId"])
    fun createComment(productId: Long, memberId: Long, content: String): ProductCommentHistoryVO {
        val commentHistory = productCommentService.createCommentHistory(productId, memberId, content)
        productCommentSummaryService.upsertProductCommentMapping(productId, memberId, commentHistory.productCommentHistoryId!!)
        return ProductCommentHistoryVO.from(commentHistory)
    }

    @DistributedLock(DistributedLockType.COMMENT, ["productId", "memberId"])
    fun deleteComment(productId: Long, memberId: Long) {
        productLikeHistoryService.cancel(productId, memberId)
        productLikeSummaryService.upsertProductLikeMapping(productId, memberId, ProductLikeType.NONE)
        productCommentSummaryService.deleteCommentMapping(productId, memberId)
    }
}
