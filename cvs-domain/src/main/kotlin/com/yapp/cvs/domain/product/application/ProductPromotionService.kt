package com.yapp.cvs.domain.product.application

import com.yapp.cvs.domain.base.vo.OffsetPageVO
import com.yapp.cvs.domain.product.entity.ProductPromotion
import com.yapp.cvs.domain.product.repository.ProductPromotionRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ProductPromotionService(
    private val productProductPromotionRepository: ProductPromotionRepository
) {
    fun findProductPromotionList(productId: Long): List<ProductPromotion> {
        return productProductPromotionRepository.findAllByProductId(productId)
    }

    fun findProductPromotionList(size: Long, offsetProductPromotionId: Long?): OffsetPageVO<ProductPromotion> {
        val productPromotionList = productProductPromotionRepository
            .findAllByPageOffset(size, offsetProductPromotionId, LocalDateTime.now())

        return OffsetPageVO(productPromotionList.lastOrNull()?.productPromotionId, productPromotionList)
    }
}