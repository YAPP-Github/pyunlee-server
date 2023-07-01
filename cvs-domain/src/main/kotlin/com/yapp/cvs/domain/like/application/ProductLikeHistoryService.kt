package com.yapp.cvs.domain.like.application

import com.yapp.cvs.domain.enums.ProductLikeType
import com.yapp.cvs.domain.like.entity.MemberProductLikeMapping
import com.yapp.cvs.domain.like.entity.ProductLikeHistory
import com.yapp.cvs.domain.like.repository.MemberProductLikeMappingRepository
import com.yapp.cvs.domain.like.repository.ProductLikeHistoryRepository
import com.yapp.cvs.exception.BadRequestException
import org.springframework.stereotype.Service

@Service
class ProductLikeHistoryService(
        private val productLikeHistoryRepository: ProductLikeHistoryRepository
) {
    fun findLatest(productId: Long, memberId: Long): ProductLikeHistory? {
        return productLikeHistoryRepository.findLatestByProductIdAndMemberId(productId, memberId)
    }

    fun like(productId: Long, memberId: Long): ProductLikeHistory {
        validateDuplicatedLike(productId, memberId, ProductLikeType.LIKE)
        val rate = ProductLikeHistory.like(productId, memberId)
        return productLikeHistoryRepository.save(rate)
    }

    fun dislike(productId: Long, memberId: Long): ProductLikeHistory {
        validateDuplicatedLike(productId, memberId, ProductLikeType.DISLIKE)
        val rate = ProductLikeHistory.dislike(productId, memberId)
        return productLikeHistoryRepository.save(rate)
    }

    fun cancel(productId: Long, memberId: Long): ProductLikeHistory {
        validateDuplicatedLike(productId, memberId, ProductLikeType.NONE)
        val rate = ProductLikeHistory.none(productId, memberId)
        return productLikeHistoryRepository.save(rate)
    }

    private fun validateDuplicatedLike(productId: Long, memberId: Long, likeType: ProductLikeType) {
        val latestType = this.findLatest(productId, memberId)?.likeType
        if (latestType == likeType) {
            throw BadRequestException("중복된 평가 요청입니다.")
        }
    }
}
