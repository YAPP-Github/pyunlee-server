package com.yapp.cvs.domain.product.repository

import com.yapp.cvs.domain.base.vo.OffsetOrderType
import com.yapp.cvs.domain.product.entity.ProductPromotion
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface ProductPromotionRepository : JpaRepository<ProductPromotion, Long>, ProductPromotionCustom {
    fun findAllByProductId(productId: Long): List<ProductPromotion>
}

interface ProductPromotionCustom {
    fun findAllByPageOffset(size: Long, offsetProductPromotionId: Long?, current: LocalDateTime): List<ProductPromotion>
}
