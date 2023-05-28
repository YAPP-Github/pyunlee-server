package com.yapp.cvs.domains.product.repository

import com.yapp.cvs.domains.product.entity.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long> {
    fun findByCodeOrName(
            code: String,
            name: String
    ): Boolean
}
