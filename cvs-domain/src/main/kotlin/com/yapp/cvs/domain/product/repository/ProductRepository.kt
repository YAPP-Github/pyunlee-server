package com.yapp.cvs.domain.product.repository

import com.yapp.cvs.domain.base.vo.OffsetPageVO
import com.yapp.cvs.domain.base.vo.OffsetSearchVO
import com.yapp.cvs.domain.enums.ProductCategoryType
import com.yapp.cvs.domain.enums.RetailerType
import com.yapp.cvs.domain.product.entity.Product
import com.yapp.cvs.domain.product.entity.ProductPromotionType
import com.yapp.cvs.domain.product.vo.ProductPbVO
import com.yapp.cvs.domain.product.vo.ProductSearchVO
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.time.LocalDateTime

interface ProductRepository : JpaRepository<Product, Long>, ProductRepositoryCustom {
    fun findByProductNameAndBrandName(productName: String, brandName: String): Product?
    fun findByBarcode(barcode: String): Product?
}

interface ProductRepositoryCustom {
    fun findWithPbInfoByProductId(productId: Long): ProductPbVO?
    fun findProductList(offsetSearchVO: OffsetSearchVO, productSearchVO: ProductSearchVO): List<Product>

    fun findProductPage(pageable: Pageable, productSearchVO: ProductSearchVO): Page<Product>
}