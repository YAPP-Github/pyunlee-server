package com.yapp.cvs.domain.product.repository

import com.yapp.cvs.domain.base.vo.OffsetPageVO
import com.yapp.cvs.domain.enums.ProductCategoryType
import com.yapp.cvs.domain.enums.RetailerType
import com.yapp.cvs.domain.product.entity.Product
import com.yapp.cvs.domain.product.entity.ProductPromotionType
import com.yapp.cvs.domain.product.vo.ProductPbVO
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.time.LocalDateTime

interface ProductRepository : JpaRepository<Product, Long>, ProductRepositoryCustom {
    fun findByProductNameAndBrandName(productName: String, brandName: String): Product?
    fun findByBarcode(barcode: String): Product?
}

interface ProductRepositoryCustom {
    fun findWithPbInfoByProductId(productId: Long): ProductPbVO?
    fun findProductList(
        minPrice: Long?,
        maxPrice: Long?,
        productCategoryTypeList: List<ProductCategoryType>,
        pbOnly: Boolean,
        promotionTypeList: List<ProductPromotionType>,
        promotionRetailerList: List<RetailerType>,
        appliedDateTime: LocalDateTime,
        pageSize: Long,
        offsetProductId: Long?
    ): List<Product>
}