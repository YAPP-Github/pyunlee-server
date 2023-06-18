package com.yapp.cvs.domain.product.repository

import com.yapp.cvs.domain.product.entity.PbProductMapping
import org.springframework.data.jpa.repository.JpaRepository

interface PbProductMappingRepository : JpaRepository<PbProductMapping, Long>
