package com.yapp.cvs.domain.like.application

import com.yapp.cvs.domain.enums.ProductLikeType
import com.yapp.cvs.domain.like.entity.ProductLikeHistory
import com.yapp.cvs.domain.like.repository.ProductLikeHistoryRepository
import com.yapp.cvs.exception.BadRequestException
import org.springframework.stereotype.Service

@Service
class ProductLikeHistoryService(
        private val productLikeHistoryRepository: ProductLikeHistoryRepository
) {
    fun findLatest(productId: Long, memberId: Long): ProductLikeHistory {
        return productLikeHistoryRepository.findLatestByProductIdAndMemberId(productId, memberId)
                ?: ProductLikeHistory.none(productId, memberId)
    }

    fun findLatestType(productId: Long, memberId: Long): ProductLikeType {
        return this.findLatest(productId, memberId).likeType
    }

    fun like(productId: Long, memberId: Long): ProductLikeHistory {
        validateDuplicatedLike(productId, memberId, ProductLikeType.LIKE)
        return ProductLikeHistory.like(productId, memberId)
    }

    fun dislike(productId: Long, memberId: Long): ProductLikeHistory {
        validateDuplicatedLike(productId, memberId, ProductLikeType.DISLIKE)
        return ProductLikeHistory.dislike(productId, memberId)
    }

    fun cancel(productId: Long, memberId: Long): ProductLikeHistory {
        validateDuplicatedLike(productId, memberId, ProductLikeType.NONE)
        return ProductLikeHistory.none(productId, memberId)
    }

    private fun validateDuplicatedLike(productId: Long, memberId: Long, likeType: ProductLikeType) {
        val latestType = this.findLatestType(productId, memberId)
        if (latestType == likeType) {
            throw BadRequestException("중복된 평가 요청입니다.")
        }
    }
}
