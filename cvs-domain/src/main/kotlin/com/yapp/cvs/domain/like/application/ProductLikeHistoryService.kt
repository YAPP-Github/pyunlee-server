package com.yapp.cvs.domain.like.application

import com.yapp.cvs.domain.enums.DistributedLockType
import com.yapp.cvs.domain.enums.ProductLikeType
import com.yapp.cvs.domain.like.entity.ProductLikeHistory
import com.yapp.cvs.domain.like.repository.ProductLikeHistoryRepository
import com.yapp.cvs.domain.like.vo.ProductLikeVO
import com.yapp.cvs.exception.BadRequestException
import com.yapp.cvs.infrastructure.redis.lock.DistributedLock
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductLikeHistoryService(
        private val productLikeHistoryRepository: ProductLikeHistoryRepository
) {
    fun findLatestByProductIdAndMemberId(productId: Long, memberId: Long): ProductLikeVO {
        return productLikeHistoryRepository.findLatestByProductIdAndMemberId(productId, memberId)
                ?: ProductLikeVO.noneOf(productId)
    }

    @Transactional
    @DistributedLock(DistributedLockType.LIKE, ["productId", "memberId"])
    fun like(productId: Long, memberId: Long): ProductLikeVO {
        return createProductLike(productId, memberId, ProductLikeType.LIKE)
    }

    @Transactional
    @DistributedLock(DistributedLockType.LIKE, ["productId", "memberId"])
    fun dislike(productId: Long, memberId: Long): ProductLikeVO {
        return createProductLike(productId, memberId, ProductLikeType.DISLIKE)
    }

    @Transactional
    @DistributedLock(DistributedLockType.LIKE, ["productId", "memberId"])
    fun cancel(productId: Long, memberId: Long): ProductLikeVO {
        return createProductLike(productId, memberId, ProductLikeType.NONE)
    }

    private fun createProductLike(productId: Long, memberId: Long, likeType: ProductLikeType): ProductLikeVO {
        validateDuplicatedLike(productId, memberId, likeType)
        val productLike = ProductLikeHistory.of(productId, memberId, likeType)
        return ProductLikeVO.from(productLikeHistoryRepository.save(productLike))
    }

    private fun validateDuplicatedLike(productId: Long, memberId: Long, likeType: ProductLikeType) {
        if (productLikeHistoryRepository.existsLatestByProductIdAndMemberIdAndType(productId, memberId, likeType)) {
            throw BadRequestException("중복된 평가 요청입니다.")
        }
    }
}
