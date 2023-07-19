package com.yapp.cvs.domain.comment.repository.impl

import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.Projections
import com.yapp.cvs.domain.comment.entity.ProductComment
import com.yapp.cvs.domain.comment.entity.ProductCommentOrderType
import com.yapp.cvs.domain.comment.entity.QProductComment.productComment
import com.yapp.cvs.domain.comment.entity.QProductCommentRatingSummary.productCommentRatingSummary
import com.yapp.cvs.domain.comment.repository.ProductCommentRepositoryCustom
import com.yapp.cvs.domain.comment.view.ProductCommentDetailView
import com.yapp.cvs.domain.comment.view.ProductCommentView
import com.yapp.cvs.domain.comment.vo.ProductCommentSearchVO
import com.yapp.cvs.domain.like.entity.QMemberProductLikeMapping.memberProductLikeMapping
import com.yapp.cvs.domain.product.entity.QProduct.product
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

    override fun findByProductIdAndSearchCondition(productId: Long, productCommentSearchVO: ProductCommentSearchVO): List<ProductCommentView> {
        val predicate = productComment.valid.isTrue
                .and(productComment.productId.eq(productId))
                .and(productCommentSearchVO.offsetProductCommentId?.let { productComment.productCommentId.lt(it) })

        return from(productComment)
            .leftJoin(productCommentRatingSummary)
            .on(productComment.productCommentId.eq(productCommentRatingSummary.productCommentId))
            .leftJoin(memberProductLikeMapping)
            .on(productComment.memberId.eq(memberProductLikeMapping.memberId).and(productComment.productId.eq(memberProductLikeMapping.productId)))
            .where(predicate)
            .orderBy(getOrderBy(productCommentSearchVO.orderBy), productComment.productCommentId.desc())
            .limit(productCommentSearchVO.pageSize)
            .select(Projections.constructor(ProductCommentView::class.java,
                productComment,
                productCommentRatingSummary.likeCount,
                memberProductLikeMapping.likeType)
            ).fetch()
    }

    override fun findRecentCommentList(size: Int): List<ProductCommentDetailView> {
        return from(productComment)
            .innerJoin(product)
            .on(productComment.productId.eq(product.productId))
            .leftJoin(memberProductLikeMapping)
            .on(productComment.memberId.eq(memberProductLikeMapping.memberId).and(productComment.productId.eq(memberProductLikeMapping.memberId)))
            .leftJoin(productCommentRatingSummary)
            .on(productComment.productCommentId.eq(productCommentRatingSummary.productCommentId))
            .orderBy(productComment.productId.desc())
            .limit(size.toLong())
            .select(Projections.constructor(ProductCommentDetailView::class.java,
                productComment,
                productCommentRatingSummary.likeCount,
                product,
                memberProductLikeMapping.likeType)
            ).fetch()
    }

    private fun getOrderBy(productCommentOrderType: ProductCommentOrderType): OrderSpecifier<*>? {
        return if (productCommentOrderType == ProductCommentOrderType.LIKE) {
            productCommentRatingSummary.likeCount.desc()
        } else {
            productComment.productCommentId.desc()
        }
    }
}
