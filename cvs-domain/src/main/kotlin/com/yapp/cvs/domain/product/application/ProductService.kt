package com.yapp.cvs.domain.product.application

import com.yapp.cvs.domain.product.repository.ProductRepository
import com.yapp.cvs.domain.product.vo.ProductPbVO
import com.yapp.cvs.exception.NotFoundSourceException
import com.yapp.cvs.infrastructure.redis.RedisKey
import com.yapp.cvs.infrastructure.redis.RedisKeyType
import com.yapp.cvs.infrastructure.redis.service.RedisService
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val redisService: RedisService
) {
    fun findProductPbInfo(productId: Long): ProductPbVO {
        return productRepository.findWithPbInfoByProductId(productId)
            ?: throw NotFoundSourceException("productId: $productId 가 존재하지 않습니다")
    }

    fun increaseProductViewCount(productId: Long) {
        redisService.increment(RedisKey.createKey(RedisKeyType.PRODUCT_VIEW, productId.toString()))
    }

}
