package com.yapp.cvs.domain.like.repository.impl

import com.querydsl.core.types.ExpressionUtils.count
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.JPAExpressions.select
import com.yapp.cvs.domain.enums.ProductLikeType
import com.yapp.cvs.domain.like.entity.MemberProductLikeMapping
import com.yapp.cvs.domain.like.entity.QMemberProductLikeMapping.memberProductLikeMapping
import com.yapp.cvs.domain.like.repository.MemberProductLikeMappingCustom
import com.yapp.cvs.domain.like.vo.ProductLikeSummaryVO
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class MemberProductLikeMappingRepositoryImpl
    : QuerydslRepositorySupport(MemberProductLikeMapping::class.java), MemberProductLikeMappingCustom
{
    override fun findAllByProductId(productId: Long): List<MemberProductLikeMapping> {
                return from(memberProductLikeMapping)
                .where(
                        memberProductLikeMapping.productId.eq(productId),
                        memberProductLikeMapping.likeType.eq(ProductLikeType.NONE).not()
                ).fetch()
    }

    override fun findAllByMemberId(memberId: Long): List<MemberProductLikeMapping> {
        return from(memberProductLikeMapping)
                .where(
                        memberProductLikeMapping.memberId.eq(memberId),
                        memberProductLikeMapping.likeType.eq(ProductLikeType.NONE).not()
                ).fetch()
    }
}
