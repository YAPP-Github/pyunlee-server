package com.yapp.cvs.domain.comment.repository.impl

import com.querydsl.core.types.ConstructorExpression
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.Projections
import com.yapp.cvs.domain.comment.entity.ProductComment
import com.yapp.cvs.domain.comment.entity.ProductCommentOrderType
import com.yapp.cvs.domain.comment.entity.QProductComment.productComment
import com.yapp.cvs.domain.comment.entity.QProductCommentLike.productCommentLike
import com.yapp.cvs.domain.comment.entity.QProductCommentLikeSummary.productCommentLikeSummary
import com.yapp.cvs.domain.comment.repository.ProductCommentRepositoryCustom
import com.yapp.cvs.domain.comment.vo.ProductCommentDetailVO
import com.yapp.cvs.domain.comment.vo.ProductCommentSearchVO
import com.yapp.cvs.domain.like.entity.QMemberProductLikeMapping.memberProductLikeMapping
import com.yapp.cvs.domain.member.entity.QMember.member
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class ProductCommentRepositoryRepositoryImpl: QuerydslRepositorySupport(ProductComment::class.java), ProductCommentRepositoryCustom {
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

    override fun findAllByCondition(productCommentSearchVO: ProductCommentSearchVO): List<ProductCommentDetailVO> {
        val predicate = productComment.valid.isTrue
                .and(productCommentSearchVO.productId?.let { productComment.productId.eq(it) })
                .and(productCommentSearchVO.offsetProductCommentId?.let { productComment.productCommentId.lt(it) })

        return from(productComment)
                .leftJoin(member)
                .on(productComment.memberId.eq(member.memberId))
                .leftJoin(memberProductLikeMapping)
                .on(
                        productComment.productId.eq(memberProductLikeMapping.productId),
                        productComment.memberId.eq(memberProductLikeMapping.memberId)
                )
                .leftJoin(productCommentLike)
                .on(
                        productCommentLike.valid.isTrue,
                        productCommentSearchVO.memberId ?.let { productCommentLike.likeMemberId.eq(it) },
                        productComment.productId.eq(productCommentLike.productId),
                        productComment.memberId.eq(productCommentLike.memberId)
                )
                .leftJoin(productCommentLikeSummary)
                .on(
                        productComment.productId.eq(productCommentLikeSummary.productId),
                        productComment.memberId.eq(productCommentLikeSummary.memberId),
                )
                .where(predicate)
                .orderBy(getOrderBy(productCommentSearchVO.orderBy))
                .limit(productCommentSearchVO.pageSize)
                .select(productCommentDetailVOProjection(productCommentSearchVO.memberId))
                .fetch()
    }

    override fun findRecentCommentList(size: Int): List<ProductComment> {
        return from(productComment)
            .orderBy(productComment.productId.desc())
            .limit(size.toLong())
            .fetch()
    }

    private fun productCommentDetailVOProjection(memberId: Long?): ConstructorExpression<ProductCommentDetailVO>? {
        return Projections.constructor(
                ProductCommentDetailVO::class.java,
                productComment.productCommentId,
                productComment.content,
                productCommentLikeSummary.likeCount,
                productComment.createdAt,
                memberProductLikeMapping.likeType,
                productComment.productId,
                productComment.memberId,
                member.nickName,
                productCommentLike.isNotNull,
                memberId?.let { productComment.memberId.eq(memberId) }
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
