package com.yapp.cvs.domain.like.repository.impl

import com.querydsl.core.types.Projections
import com.yapp.cvs.domain.enums.ProductLikeType
import com.yapp.cvs.domain.like.entity.ProductLikeHistory
import com.yapp.cvs.domain.like.entity.QProductLikeHistory.productLikeHistory
import com.yapp.cvs.domain.like.repository.ProductLikeHistoryRepositoryCustom
import com.yapp.cvs.domain.like.vo.ProductLikeVO
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class ProductLikeHistoryRepositoryImpl:
        QuerydslRepositorySupport(ProductLikeHistory::class.java), ProductLikeHistoryRepositoryCustom
{
    override fun findLatestByProductIdAndMemberId(productId: Long, memberId: Long): ProductLikeVO? {
        return from(productLikeHistory)
            .where(
                    productLikeHistory.productId.eq(productId),
                    productLikeHistory.memberId.eq(memberId),
            )
            .orderBy(productLikeHistory.createdAt.desc())
                .select(Projections.constructor(
                        ProductLikeVO::class.java,
                        productLikeHistory.productId,
                        productLikeHistory.likeType
                ))
            .fetchFirst()
    }

    override fun existsLatestByProductIdAndMemberIdAndType(productId: Long, memberId: Long, type: ProductLikeType): Boolean {
        val latestLikeHistory = from(productLikeHistory)
                .select(productLikeHistory.likeType)
                .where(
                        productLikeHistory.productId.eq(productId),
                        productLikeHistory.memberId.eq(memberId),
                )
                .orderBy(productLikeHistory.createdAt.desc())
                .fetchFirst()

        return if (type == ProductLikeType.NONE) {
            latestLikeHistory == null || latestLikeHistory == ProductLikeType.NONE
        } else {
            latestLikeHistory == type
        }
    }
}
