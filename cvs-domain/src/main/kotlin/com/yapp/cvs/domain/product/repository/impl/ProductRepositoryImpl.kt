package com.yapp.cvs.domain.product.repository.impl

import com.querydsl.core.types.Projections
import com.yapp.cvs.domain.product.entity.Product
import com.yapp.cvs.domain.product.repository.ProductRepositoryCustom
import com.yapp.cvs.domain.product.vo.ProductPbVO
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import com.yapp.cvs.domain.product.entity.QProduct.product
import com.yapp.cvs.domain.product.entity.QPbProductMapping.pbProductMapping
@Repository
class ProductRepositoryImpl: QuerydslRepositorySupport(Product::class.java), ProductRepositoryCustom {
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
}