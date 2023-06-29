package com.yapp.cvs.domain.like.repository.impl

import com.yapp.cvs.domain.like.entity.ProductLikeHistory
import com.yapp.cvs.domain.like.entity.QProductLikeHistory.productLikeHistory
import com.yapp.cvs.domain.like.repository.ProductLikeHistoryCustom
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class ProductLikeHistoryRepositoryImpl: QuerydslRepositorySupport(ProductLikeHistory::class.java), ProductLikeHistoryCustom
{
    override fun findLatestByProductIdAndMemberId(productId: Long, memberId: Long): ProductLikeHistory? {
        return from(productLikeHistory)
            .where(
                    productLikeHistory.productId.eq(productId),
                    productLikeHistory.memberId.eq(memberId),
            )
            .orderBy(productLikeHistory.productLikeHistoryId.desc())
            .fetchFirst()
    }


}
