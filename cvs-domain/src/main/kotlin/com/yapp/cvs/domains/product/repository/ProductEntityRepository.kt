package com.yapp.cvs.domains.product.repository

import com.yapp.cvs.domains.product.entity.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProductEntityRepository: JpaRepository<ProductEntity, Long> {
    fun findByProductNameAndBrandName(productName: String, brandName: String): ProductEntity?
}