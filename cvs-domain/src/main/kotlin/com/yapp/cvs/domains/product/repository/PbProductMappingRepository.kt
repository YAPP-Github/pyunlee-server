package com.yapp.cvs.domains.product.repository

import com.yapp.cvs.domains.product.entity.PbProductMapping
import org.springframework.data.jpa.repository.JpaRepository

interface PbProductMappingRepository: JpaRepository<PbProductMapping, Long> {
}