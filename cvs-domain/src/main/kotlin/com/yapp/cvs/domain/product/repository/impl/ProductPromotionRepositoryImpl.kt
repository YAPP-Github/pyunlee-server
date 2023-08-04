package com.yapp.cvs.domain.product.repository.impl

import com.yapp.cvs.domain.product.entity.ProductPromotion
import com.yapp.cvs.domain.product.entity.QProductScore.productScore
import com.yapp.cvs.domain.product.entity.QProductPromotion.productPromotion
import com.yapp.cvs.domain.product.repository.ProductPromotionCustom
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class ProductPromotionRepositoryImpl:  QuerydslRepositorySupport(ProductPromotion::class.java), ProductPromotionCustom {
    override fun findAllByPageOffset(size: Long, offsetProductPromotionId: Long?, current: LocalDateTime): List<ProductPromotion> {
        var predicate = productPromotion.validAt.gt(current)

        if (offsetProductPromotionId != null){
            predicate = predicate.and(productPromotion.productPromotionId.gt(offsetProductPromotionId))
        }

        return from(productPromotion)
            .leftJoin(productScore)
            .on(productPromotion.productId.eq(productScore.productId))
            .where(predicate)
            .orderBy(productScore.score.desc(), productPromotion.productPromotionId.desc())
            .limit(size)
            .fetch()
    }
}