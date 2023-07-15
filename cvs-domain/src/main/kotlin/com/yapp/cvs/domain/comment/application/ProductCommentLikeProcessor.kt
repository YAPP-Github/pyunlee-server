package com.yapp.cvs.domain.comment.application

import com.yapp.cvs.domain.comment.vo.ProductCommentLikeRequestVO
import com.yapp.cvs.domain.comment.vo.ProductCommentLikeVO
import com.yapp.cvs.domain.enums.DistributedLockType
import com.yapp.cvs.domain.like.entity.MemberProductMappingKey
import com.yapp.cvs.infrastructure.redis.lock.DistributedLock
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ProductCommentLikeProcessor(
        private val productCommentLikeService: ProductCommentLikeService,
        private val productCommentLikeSummaryService: ProductCommentLikeSummaryService
) {
    @DistributedLock(DistributedLockType.MEMBER_PRODUCT, ["productCommentLikeRequestVO"])
    fun likeComment(productCommentLikeRequestVO: ProductCommentLikeRequestVO, memberId: Long): ProductCommentLikeVO {
        val mappingKey = productCommentLikeRequestVO.memberProductMappingKey
        val commentLike = productCommentLikeService.like(mappingKey, memberId)
        productCommentLikeSummaryService.increaseLikeCount(mappingKey)
        return ProductCommentLikeVO.from(commentLike)
    }

    @DistributedLock(DistributedLockType.MEMBER_PRODUCT, ["productCommentLikeRequestVO"])
    fun cancelCommentLike(productCommentLikeRequestVO: ProductCommentLikeRequestVO, memberId: Long): ProductCommentLikeVO {
        val mappingKey = productCommentLikeRequestVO.memberProductMappingKey
        val commentLike = productCommentLikeService.cancel(mappingKey, memberId)
        productCommentLikeSummaryService.cancelLikeCount(mappingKey)
        return ProductCommentLikeVO.from(commentLike)
    }
}
