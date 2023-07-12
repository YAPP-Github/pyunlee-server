package com.yapp.cvs.domain.product.repository.impl

import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.yapp.cvs.domain.base.vo.OffsetSearchVO
import com.yapp.cvs.domain.extension.ifNotEmpty
import com.yapp.cvs.domain.extension.ifNotNull
import com.yapp.cvs.domain.extension.ifTrue
import com.yapp.cvs.domain.product.entity.Product
import com.yapp.cvs.domain.product.entity.ProductOrderType
import com.yapp.cvs.domain.product.entity.QPbProductMapping.pbProductMapping
import com.yapp.cvs.domain.product.entity.QProduct.product
import com.yapp.cvs.domain.product.entity.QProductPromotion.productPromotion
import com.yapp.cvs.domain.like.entity.QProductLikeSummary.productLikeSummary
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
            .and(offsetSearchVO.offsetId.ifNotNull { product.productId.lt(offsetSearchVO.offsetId) })

        return from(product)
            .leftJoin(product.productLikeSummaryList, productLikeSummary)
            .fetchJoin()
            .leftJoin(product.pbProductMappingList, pbProductMapping)
            .fetchJoin()
            .leftJoin(product.productPromotionList, productPromotion)
            .fetchJoin()
            .where(predicate)
            .orderBy(getOrderBy(productSearchVO.orderBy), product.productId.desc())
            .limit(offsetSearchVO.pageSize.toLong())
            .select(product)
            .fetch()
    }

    override fun findProductPage(pageable: Pageable, productSearchVO: ProductSearchVO): Page<Product> {
        val predicate = productSearchWhere(productSearchVO)

        val result =  from(product)
            .leftJoin(product.pbProductMappingList, pbProductMapping)
            .fetchJoin()
            .leftJoin(product.productPromotionList, productPromotion)
            .fetchJoin()
            .where(predicate)
            .orderBy(getOrderBy(productSearchVO.orderBy), product.productId.desc())
            .limit(pageable.pageSize.toLong())
            .offset(pageable.offset)
            .select(product)
            .fetchResults()

        return  PageImpl(result.results, pageable, result.total)
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
    private fun getOrderBy(productOrderType: ProductOrderType): OrderSpecifier<*>{
        if(productOrderType == ProductOrderType.RECENT){
            return product.productId.desc()
        }else {
            //TODO: 평가 기능 후 정렬기능 추가
            return product.createdAt.desc()
        }
    }
}