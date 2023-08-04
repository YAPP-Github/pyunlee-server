package com.yapp.cvs.domain.product.repository.impl

import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import com.yapp.cvs.domain.base.vo.OffsetSearchVO
import com.yapp.cvs.domain.enums.ProductLikeType
import com.yapp.cvs.domain.extension.ifNotEmpty
import com.yapp.cvs.domain.extension.ifNotNull
import com.yapp.cvs.domain.extension.ifTrue
import com.yapp.cvs.domain.like.entity.QMemberProductLikeMapping.memberProductLikeMapping
import com.yapp.cvs.domain.like.entity.QProductLikeSummary.productLikeSummary
import com.yapp.cvs.domain.product.entity.Product
import com.yapp.cvs.domain.product.entity.ProductOrderType
import com.yapp.cvs.domain.product.entity.QPbProductMapping.pbProductMapping
import com.yapp.cvs.domain.product.entity.QProduct.product
import com.yapp.cvs.domain.product.entity.QProductPromotion.productPromotion
import com.yapp.cvs.domain.product.entity.QProductScore.productScore
import com.yapp.cvs.domain.product.repository.ProductRepositoryCustom
import com.yapp.cvs.domain.product.vo.ProductSearchVO
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository


@Repository
class ProductRepositoryImpl : QuerydslRepositorySupport(Product::class.java), ProductRepositoryCustom {

    override fun findByProductId(productId: Long): Product? {
        return from(product)
            .leftJoin(product.productLikeSummaryList, productLikeSummary)
            .fetchJoin()
            .leftJoin(product.pbProductMappingList, pbProductMapping)
            .fetchJoin()
            .leftJoin(product.productPromotionList, productPromotion)
            .fetchJoin()
            .where(product.productId.eq(productId))
            .select(product).fetchFirst()
    }

    override fun findProductList(offsetSearchVO: OffsetSearchVO, productSearchVO: ProductSearchVO): List<Product> {
        val predicate = productSearchWhere(productSearchVO)
            .and(offsetSearchVO.offsetId.ifNotNull {
                product.productId.lt(offsetSearchVO.offsetId)
                    .and(productScore.score.loe(getLastProductScore(offsetSearchVO.offsetId)))
            })

        return from(product)
            .leftJoin(product.productLikeSummaryList, productLikeSummary)
            .fetchJoin()
            .leftJoin(product.pbProductMappingList, pbProductMapping)
            .fetchJoin()
            .leftJoin(product.productPromotionList, productPromotion)
            .fetchJoin()
            .innerJoin(productScore)
            .on(product.productId.eq(productScore.productId))
            .where(predicate)
            .orderBy(getOrderBy(productSearchVO.orderBy), product.productId.desc())
            .limit(offsetSearchVO.pageSize.toLong())
            .select(product)
            .fetch()
    }

    override fun findProductPage(pageable: Pageable, productSearchVO: ProductSearchVO): Page<Product> {
        val predicate = productSearchWhere(productSearchVO)

        val result = from(product)
            .leftJoin(product.pbProductMappingList, pbProductMapping)
            .fetchJoin()
            .leftJoin(product.productPromotionList, productPromotion)
            .fetchJoin()
            .leftJoin(productScore)
            .on(product.productId.eq(productScore.productId))
            .where(predicate)
            .orderBy(getOrderBy(productSearchVO.orderBy), product.productId.desc())
            .limit(pageable.pageSize.toLong())
            .offset(pageable.offset)
            .select(product)
            .fetchResults()

        return  PageImpl(result.results, pageable, result.total)
    }

    override fun findUnratedProductList(memberId: Long, offsetProductId: Long?, pageSize: Int): List<Product> {
        return from(product)
            .leftJoin(product.pbProductMappingList, pbProductMapping)
            .fetchJoin()
            .leftJoin(productScore)
            .on(product.productId.eq(productScore.productId))
            .leftJoin(memberProductLikeMapping)
            .on(product.productId.eq(memberProductLikeMapping.productId)
                .and(memberProductLikeMapping.memberId.eq(memberId)))
            .where((memberProductLikeMapping.isNull.or(memberProductLikeMapping.likeType.eq(ProductLikeType.NONE)))
                .and(offsetProductId.ifNotNull {
                    product.productId.lt(offsetProductId)
                        .and(productScore.score.loe(getLastProductScore(offsetProductId)))
                })
                .and(product.valid.eq(true)))
            .orderBy(productScore.score.desc(), product.productId.desc())
            .limit(pageSize.toLong())
            .select(product)
            .fetch()
    }

    private fun productSearchWhere(productSearchVO: ProductSearchVO): BooleanExpression {
        return product.valid.eq(true)
            .and(productSearchVO.minPrice.ifNotNull { product.price.goe(productSearchVO.minPrice) })
            .and(productSearchVO.maxPrice.ifNotNull { product.price.loe(productSearchVO.maxPrice) })
            .and(productSearchVO.productCategoryTypeList.ifNotEmpty { product.productCategoryType.`in`(productSearchVO.productCategoryTypeList) })
            .and(productSearchVO.pbOnly.ifTrue { pbProductMapping.pbProductMappingId.isNotNull })
            .and(productSearchVO.keyWord.ifNotNull { product.productName.like("%${productSearchVO.keyWord}%")
                .or(product.brandName.like("%${productSearchVO.keyWord}%")) })
            .and(productSearchVO.promotionTypeList.isNotEmpty().or(productSearchVO.promotionRetailerList.isNotEmpty())
                .ifTrue { productPromotion.productPromotionId.isNotNull
                    .and(productSearchVO.promotionTypeList.ifNotEmpty { productPromotion.promotionType.`in`(productSearchVO.promotionTypeList) })
                    .and(productSearchVO.promotionRetailerList.ifNotEmpty { productPromotion.retailerType.`in`(productSearchVO.promotionRetailerList) })
                    .and(productPromotion.validAt.gt(productSearchVO.appliedDateTime))
                })
    }

    private fun getOrderBy(productOrderType: ProductOrderType): OrderSpecifier<*>?{
        if (productOrderType == ProductOrderType.RECENT){
            return product.productId.desc()
        }else {
            return productScore.score.desc()
        }
    }

    private fun getLastProductScore(offsetProductId: Long?): Long {
        if (offsetProductId == null) return 0L

        return from(product)
            .where(product.productId.eq(offsetProductId))
            .innerJoin(productScore)
            .on(product.productId.eq(productScore.productId))
            .select(productScore.score)
            .fetchOne()
    }
}