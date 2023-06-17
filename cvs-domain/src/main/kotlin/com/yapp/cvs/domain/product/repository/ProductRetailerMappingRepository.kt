package com.yapp.cvs.domain.product.repository

import com.yapp.cvs.domain.product.entity.ProductRetailerMapping
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRetailerMappingRepository : JpaRepository<ProductRetailerMapping, Long>
