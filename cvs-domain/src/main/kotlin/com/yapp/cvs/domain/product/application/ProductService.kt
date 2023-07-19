package com.yapp.cvs.domain.product.application

import com.yapp.cvs.domain.base.vo.OffsetSearchVO
import com.yapp.cvs.domain.base.vo.PageSearchVO
import com.yapp.cvs.domain.base.vo.PageVO
import com.yapp.cvs.domain.member.entity.Member
import com.yapp.cvs.domain.product.entity.Product
import com.yapp.cvs.domain.product.repository.ProductRepository
import com.yapp.cvs.domain.product.vo.ProductPbVO
import com.yapp.cvs.domain.product.vo.ProductSearchVO
import com.yapp.cvs.exception.NotFoundSourceException
import com.yapp.cvs.infrastructure.redis.RedisKey
import com.yapp.cvs.infrastructure.redis.RedisKeyType
import com.yapp.cvs.infrastructure.redis.service.RedisService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val redisService: RedisService
) {
    fun findProductById(productId: Long): Product? {
        return productRepository.findById(productId).orElse(null)
    }

    fun saveProduct(product: Product) {
        productRepository.save(product)
    }

    fun findProduct(productId: Long): Product {
        return productRepository.findByProductId(productId)
            ?: throw NotFoundSourceException("productId: $productId 가 존재하지 않습니다")
    }

    fun increaseProductViewCount(productId: Long) {
        redisService.increment(RedisKey.createKey(RedisKeyType.PRODUCT_VIEW, productId.toString()))
    }

    fun searchProductList(offsetSearchVO: OffsetSearchVO, productSearchVO: ProductSearchVO): List<Product> {
        return productRepository.findProductList(offsetSearchVO, productSearchVO)
    }

    fun searchProductPage(pageSearchVO: PageSearchVO, productSearchVO: ProductSearchVO): Page<Product> {
        return productRepository.findProductPage(
            PageRequest.of(pageSearchVO.pageNum, pageSearchVO.pageSize),
            productSearchVO
        )
    }

    fun findUnratedProductList(member: Member, offsetProductId: Long?, pageSize: Int): List<Product> {
        return productRepository.findUnratedProductList(member.memberId!!, offsetProductId, pageSize)
    }
}
