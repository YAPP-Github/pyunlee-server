package com.yapp.cvs.domain.product.repository

import com.yapp.cvs.domain.base.vo.OffsetSearchVO
import com.yapp.cvs.domain.product.entity.Product
import com.yapp.cvs.domain.product.vo.ProductSearchVO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long>, ProductRepositoryCustom {
    fun findByProductNameAndBrandName(productName: String, brandName: String): Product?
    fun findByBarcode(barcode: String): Product?
}

interface ProductRepositoryCustom {
    fun findByProductId(productId: Long): Product?
    fun findProductList(offsetSearchVO: OffsetSearchVO, productSearchVO: ProductSearchVO): List<Product>
    fun findProductPage(pageable: Pageable, productSearchVO: ProductSearchVO): Page<Product>

    fun findUnratedProductList(memberId: Long, offsetProductId: Long?, pageSize: Int): List<Product>
}