package com.yapp.cvs.domain.product.repository

import com.yapp.cvs.domain.product.entity.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long> {
    fun findByProductNameAndBrandName(productName: String, brandName: String): Product?
    fun findByBarcode(barcode: String): Product?
}
