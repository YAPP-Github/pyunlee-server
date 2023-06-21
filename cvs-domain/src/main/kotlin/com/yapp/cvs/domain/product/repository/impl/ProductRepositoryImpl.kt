package com.yapp.cvs.domain.product.repository.impl

import com.querydsl.core.types.Projections
import com.yapp.cvs.domain.enums.ProductCategoryType
import com.yapp.cvs.domain.enums.RetailerType
import com.yapp.cvs.domain.extension.ifNotEmpty
import com.yapp.cvs.domain.extension.ifNotNull
import com.yapp.cvs.domain.extension.ifTrue
import com.yapp.cvs.domain.product.entity.Product
import com.yapp.cvs.domain.product.entity.ProductPromotionType
import com.yapp.cvs.domain.product.entity.QPbProductMapping.pbProductMapping
import com.yapp.cvs.domain.product.entity.QProduct.product
import com.yapp.cvs.domain.product.entity.QProductPromotion.productPromotion
import com.yapp.cvs.domain.product.repository.ProductRepositoryCustom
import com.yapp.cvs.domain.product.vo.ProductPbVO
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

    override fun findProductList(
        minPrice: Long?,
        maxPrice: Long?,
        productCategoryTypeList: List<ProductCategoryType>,
        pbOnly: Boolean,
        promotionTypeList: List<ProductPromotionType>,
        promotionRetailerList: List<RetailerType>,
        appliedDateTime: LocalDateTime,
        pageSize: Long,
        offsetProductId: Long?
    ): List<Product> {
        val predicate = product.valid.eq(true)
            .and(minPrice.ifNotNull { product.price.goe(minPrice) })
            .and(maxPrice.ifNotNull { product.price.loe(maxPrice) })
            .and(productCategoryTypeList.ifNotEmpty { product.productCategoryType.`in`(productCategoryTypeList) })
            .and(pbOnly.ifTrue { pbProductMapping.pbProductMappingId.isNotNull })
            .and(promotionTypeList.isNotEmpty().or(promotionRetailerList.isNotEmpty())
                .ifTrue { productPromotion.productPromotionId.isNotNull
                    .and(promotionTypeList.ifNotEmpty { productPromotion.promotionType.`in`(promotionTypeList) })
                    .and(promotionRetailerList.ifNotEmpty { productPromotion.retailerType.`in`(promotionRetailerList) })
                    .and(productPromotion.validAt.gt(appliedDateTime))
                })
            .and(offsetProductId.ifNotNull { product.productId.gt(offsetProductId) })

        return from(product)
            .leftJoin(product.pbProductMappingList, pbProductMapping)
            .fetchJoin()
            .leftJoin(product.productPromotionList, productPromotion)
            .fetchJoin()
            .where(predicate)
            .limit(pageSize)
            .select(product)
            .fetch()
    }
}