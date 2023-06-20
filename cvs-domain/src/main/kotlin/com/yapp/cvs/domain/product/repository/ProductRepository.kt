package com.yapp.cvs.domain.product.repository

import com.yapp.cvs.domain.product.entity.Product
import com.yapp.cvs.domain.product.vo.ProductPbVO
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long>, ProductRepositoryCustom {
    fun findByProductNameAndBrandName(productName: String, brandName: String): Product?
    fun findByBarcode(barcode: String): Product?
}

interface ProductRepositoryCustom {
    fun findWithPbInfoByProductId(productId: Long): ProductPbVO?
}