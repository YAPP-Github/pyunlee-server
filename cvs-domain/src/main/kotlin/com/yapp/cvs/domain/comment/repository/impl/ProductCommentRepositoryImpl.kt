package com.yapp.cvs.domain.comment.repository.impl

import com.querydsl.core.types.ConstructorExpression
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.Expressions
import com.yapp.cvs.domain.comment.entity.ProductComment
import com.yapp.cvs.domain.comment.entity.ProductCommentOrderType
import com.yapp.cvs.domain.comment.entity.QProductComment.productComment
import com.yapp.cvs.domain.comment.repository.ProductCommentCustom
import com.yapp.cvs.domain.comment.vo.ProductCommentDetailVO
import com.yapp.cvs.domain.comment.vo.ProductCommentSearchVO
import com.yapp.cvs.domain.like.entity.QMemberProductLikeMapping.memberProductLikeMapping
import com.yapp.cvs.domain.member.entity.QMember.member
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class ProductCommentRepositoryImpl: QuerydslRepositorySupport(ProductComment::class.java), ProductCommentCustom {
    override fun findLatestById(commentId: Long): ProductComment? {
        return from(productComment)
                .where(productComment.productCommentId.eq(commentId))
                .orderBy(productComment.productCommentId.desc())
                .fetchFirst()
    }

    override fun findLatestByProductIdAndMemberId(productId: Long, memberId: Long): ProductComment? {
        return from(productComment)
                .where(
                        productComment.productId.eq(productId),
                        productComment.memberId.eq(memberId),
                )
                .orderBy(productComment.productCommentId.desc())
                .fetchFirst()
    }

    override fun findAllByProductIdAndPageOffset(productId: Long,
                                                 productCommentSearchVO: ProductCommentSearchVO): List<ProductCommentDetailVO> {
        val size = productCommentSearchVO.pageSize
        val offsetId = productCommentSearchVO.offsetProductCommentId
        var predicate = productComment.productId.eq(productId)
                .and(productComment.valid.isTrue)
        if (offsetId != null) {
            predicate = predicate.and(productComment.productCommentId.lt(offsetId))
        }

        return from(productComment)
                .leftJoin(member)
                .on(productComment.memberId.eq(member.memberId))
                .leftJoin(memberProductLikeMapping)
                .on(
                        productComment.productId.eq(memberProductLikeMapping.productId),
                        productComment.memberId.eq(memberProductLikeMapping.memberId)
                )
                .where(predicate)
                .orderBy(getOrderBy(productCommentSearchVO.orderBy))
                .select(productDetailVOProjection())
                .limit(size)
                .fetch()
    }

    private fun productDetailVOProjection(): ConstructorExpression<ProductCommentDetailVO>? {
        // TODO : commentLikeCount 입력
        val tempCommentLikeCount = 10L

        return Projections.constructor(
                ProductCommentDetailVO::class.java,
                productComment.productCommentId,
                productComment.content,
                Expressions.asNumber(tempCommentLikeCount),
                productComment.createdAt,
                memberProductLikeMapping.likeType,
                productComment.productId,
                productComment.memberId,
                member.nickName,
                Expressions.FALSE
        )
    }

    private fun getOrderBy(productCommentOrderType: ProductCommentOrderType): OrderSpecifier<*> {
        return if (productCommentOrderType == ProductCommentOrderType.RECENT) {
            productComment.productCommentId.desc()
        } else {
            productComment.createdAt.desc()
        }
    }
}
