package com.yapp.cvs.domain.like.application

import com.yapp.cvs.domain.enums.DistributedLockType
import com.yapp.cvs.domain.enums.ProductLikeType
import com.yapp.cvs.domain.like.entity.MemberProductLikeMapping
import com.yapp.cvs.domain.like.entity.ProductLikeHistory
import com.yapp.cvs.domain.like.repository.MemberProductLikeMappingRepository
import com.yapp.cvs.domain.like.vo.ProductLikeHistoryVO
import com.yapp.cvs.domain.like.vo.ProductLikeSummaryVO
import com.yapp.cvs.infrastructure.redis.lock.DistributedLock
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ProductLikeProcessor(
        private val productLikeHistoryService: ProductLikeHistoryService,
        private val productLikeSummaryService: ProductLikeSummaryService,
) {
    fun findLatestRate(productId: Long, memberId: Long): ProductLikeHistoryVO {
        val productLikeHistory = productLikeHistoryService.findLatest(productId, memberId)
                ?: ProductLikeHistory.none(productId, memberId)
        return ProductLikeHistoryVO.from(productLikeHistory)
    }

    fun findProductLikeSummary(productId: Long): ProductLikeSummaryVO {
        return productLikeSummaryService.getProductLikeSummary(productId)
    }

    @DistributedLock(DistributedLockType.LIKE, ["productId", "memberId"])
    fun likeProduct(productId: Long, memberId: Long): ProductLikeHistoryVO {
        val productLikeHistory = productLikeHistoryService.like(productId, memberId)
        productLikeSummaryService.upsertProductLikeMapping(productId, memberId, ProductLikeType.LIKE)
        return ProductLikeHistoryVO.from(productLikeHistory)
    }

    @DistributedLock(DistributedLockType.LIKE, ["productId", "memberId"])
    fun dislikeProduct(productId: Long, memberId: Long): ProductLikeHistoryVO {
        val productLikeHistory = productLikeHistoryService.dislike(productId, memberId)
        productLikeSummaryService.upsertProductLikeMapping(productId, memberId, ProductLikeType.DISLIKE)
        return ProductLikeHistoryVO.from(productLikeHistory)
    }

    @DistributedLock(DistributedLockType.LIKE, ["productId", "memberId"])
    fun cancelEvaluation(productId: Long, memberId: Long): ProductLikeHistoryVO {
        val productLikeHistory = productLikeHistoryService.cancel(productId, memberId)
        productLikeSummaryService.upsertProductLikeMapping(productId, memberId, ProductLikeType.NONE)
        return ProductLikeHistoryVO.from(productLikeHistory)
    }
}
