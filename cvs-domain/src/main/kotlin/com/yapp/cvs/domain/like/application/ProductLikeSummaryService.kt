package com.yapp.cvs.domain.like.application

import com.yapp.cvs.domain.enums.ProductLikeType
import com.yapp.cvs.domain.like.repository.ProductLikeSummaryRepository
import com.yapp.cvs.infrastructure.redis.RedisKey
import com.yapp.cvs.infrastructure.redis.RedisKeyType
import com.yapp.cvs.infrastructure.redis.service.RedisService
import org.springframework.stereotype.Service


@Service
class ProductLikeSummaryService(
        private val productLikeSummaryRepository: ProductLikeSummaryRepository,
        private val redisService: RedisService
) {
    fun increase(productId: Long, likeType: ProductLikeType) {
        if (likeType != ProductLikeType.NONE) {
            val redisKey = RedisKey.createKey(RedisKeyType.PRODUCT_LIKE_HISTORY, likeType.name, productId.toString())
            redisService.increment(redisKey)
        }
    }

    fun decrease(productId: Long, likeType: ProductLikeType) {
        if (likeType != ProductLikeType.NONE) {
            val redisKey = RedisKey.createKey(RedisKeyType.PRODUCT_LIKE_HISTORY, likeType.name, productId.toString())
            redisService.decrement(redisKey)
        }
    }
}
