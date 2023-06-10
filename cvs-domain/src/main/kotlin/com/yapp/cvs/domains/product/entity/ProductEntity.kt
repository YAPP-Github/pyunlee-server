package com.yapp.cvs.domains.product.entity

import com.yapp.cvs.common.entity.BaseTimeEntity
import com.yapp.cvs.domains.enums.ProductCategoryType
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "products")
data class ProductEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val productId: Long? = null,

    val productName: String,

    val brandName: String,

    val price: Long,

    @Enumerated(EnumType.STRING)
    val productCategoryType: ProductCategoryType,

    val barcode: String,

    val imageUrl: String,

    val valid: Boolean = true
) : BaseTimeEntity()
