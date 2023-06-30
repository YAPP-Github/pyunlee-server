package com.yapp.cvs.domain.product.repository.impl

import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.Projections
import com.yapp.cvs.domain.enums.ProductCategoryType
import com.yapp.cvs.domain.enums.RetailerType
import com.yapp.cvs.domain.extension.ifNotEmpty
import com.yapp.cvs.domain.extension.ifNotNull
import com.yapp.cvs.domain.extension.ifTrue
import com.yapp.cvs.domain.product.entity.Product
import com.yapp.cvs.domain.product.entity.ProductOrderType
import com.yapp.cvs.domain.product.entity.ProductPromotionType
import com.yapp.cvs.domain.product.entity.QPbProductMapping.pbProductMapping
import com.yapp.cvs.domain.product.entity.QProduct.product
import com.yapp.cvs.domain.product.entity.QProductPromotion.productPromotion
import com.yapp.cvs.domain.product.repository.ProductRepositoryCustom
import com.yapp.cvs.domain.product.vo.ProductPbVO
import com.yapp.cvs.domain.product.vo.ProductSearchVO
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import java.time.LocalDateTime


@Repository
class ProductRepositoryImpl : QuerydslRepositorySupport(Product::class.java), ProductRepositoryCustom {
    override fun findWithPbInfoByProductId(productId: Long): ProductPbVO? {
        return from(product)
            .leftJoin(pbProductMapping)
            .on(product.productId.eq(pbProductMapping.productId))
            .where(product.productId.eq(productId))
            .select(Projections.constructor(
                ProductPbVO::class.java,
                product.productId,
                product.brandName,
                product.productName,
                product.price,
                product.productCategoryType,
                pbProductMapping.pbProductMappingId.isNotNull,
                product.imageUrl)
            ).fetchFirst()
    }

    override fun findProductList(productSearchVO: ProductSearchVO): List<Product> {
        val predicate = product.valid.eq(true)
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
            .and(productSearchVO.offsetProductId.ifNotNull { product.productId.lt(productSearchVO.offsetProductId) })

        return from(product)
            .leftJoin(product.pbProductMappingList, pbProductMapping)
            .fetchJoin()
            .leftJoin(product.productPromotionList, productPromotion)
            .fetchJoin()
            .where(predicate)
            .orderBy(getOrderBy(productSearchVO.orderBy), product.productId.desc())
            .limit(productSearchVO.pageSize)
            .select(product)
            .fetch()
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