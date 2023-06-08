package com.yapp.cvs.domains.product.repository

import com.yapp.cvs.domains.product.entity.ProductRetailerMapping
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRetailerMappingRepository : JpaRepository<ProductRetailerMapping, Long>
