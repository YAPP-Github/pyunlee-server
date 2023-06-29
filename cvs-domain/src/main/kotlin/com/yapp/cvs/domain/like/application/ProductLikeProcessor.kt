package com.yapp.cvs.domain.like.application

import com.yapp.cvs.domain.enums.DistributedLockType
import com.yapp.cvs.domain.enums.ProductLikeType
import com.yapp.cvs.domain.like.entity.ProductLikeHistory
import com.yapp.cvs.infrastructure.redis.lock.DistributedLock
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ProductLikeProcessor(
        private val productLikeHistoryService: ProductLikeHistoryService,
        private val productLikeSummaryService: ProductLikeSummaryService
) {
    fun findLatestRate(productId: Long, memberId: Long): ProductLikeHistory {
        return productLikeHistoryService.findLatest(productId, memberId)
    }

    @DistributedLock(DistributedLockType.LIKE, ["productId", "memberId"])
    fun likeProduct(productId: Long, memberId: Long): ProductLikeHistory {
        updateCache(productId, memberId, ProductLikeType.LIKE)
        return productLikeHistoryService.like(productId, memberId)
    }

    @DistributedLock(DistributedLockType.LIKE, ["productId", "memberId"])
    fun dislikeProduct(productId: Long, memberId: Long): ProductLikeHistory {
        updateCache(productId, memberId, ProductLikeType.DISLIKE)
        return productLikeHistoryService.dislike(productId, memberId)
    }

    @DistributedLock(DistributedLockType.LIKE, ["productId", "memberId"])
    fun cancelEvaluation(productId: Long, memberId: Long): ProductLikeHistory {
        updateCache(productId, memberId, ProductLikeType.NONE)
        return productLikeHistoryService.cancel(productId, memberId)
    }

    private fun updateCache(productId: Long, memberId: Long, likeType: ProductLikeType) {
        val latestType = productLikeHistoryService.findLatestType(productId, memberId)
        productLikeSummaryService.decrease(productId, latestType)
        productLikeSummaryService.increase(productId, likeType)
    }
}
