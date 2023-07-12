package com.yapp.cvs.domain.comment.application

import com.yapp.cvs.domain.base.vo.OffsetPageVO
import com.yapp.cvs.domain.comment.vo.ProductCommentDetailVO
import com.yapp.cvs.domain.comment.vo.ProductCommentSearchVO
import com.yapp.cvs.domain.comment.vo.ProductCommentVO
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
        private val productCommentService: ProductCommentService
) {
    fun getCommentDetails(productId: Long, memberId: Long, productCommentSearchVO: ProductCommentSearchVO): OffsetPageVO<ProductCommentDetailVO> {
        val result = productCommentService.findProductCommentsPage(productId, productCommentSearchVO)
        result.filter { it.memberId == memberId }
                .forEach { it.isOwner = true }
        return OffsetPageVO(result.lastOrNull()?.productCommentId, result)
    }

    @DistributedLock(DistributedLockType.MEMBER_PRODUCT, ["productId", "memberId"])
    fun createComment(productId: Long, memberId: Long, content: String): ProductCommentVO {
        val commentHistory = productCommentService.write(productId, memberId, content)
        return ProductCommentVO.from(commentHistory)
    }

    @DistributedLock(DistributedLockType.MEMBER_PRODUCT, ["productId", "memberId"])
    fun updateComment(productId: Long, memberId: Long, content: String): ProductCommentVO {
        val commentHistory = productCommentService.update(productId, memberId, content)
        return ProductCommentVO.from(commentHistory)
    }
}
