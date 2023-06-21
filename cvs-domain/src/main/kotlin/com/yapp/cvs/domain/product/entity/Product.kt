package com.yapp.cvs.domain.product.entity

import com.yapp.cvs.domain.base.BaseEntity
import com.yapp.cvs.domain.enums.ProductCategoryType
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "products")
data class Product(
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

    val valid: Boolean = true,

    @OneToMany
    @JoinColumn(name = "productId", insertable = false, updatable = false)
    val productPromotionList: Set<ProductPromotion> = setOf(),

    @OneToMany
    @JoinColumn(name = "productId", insertable = false, updatable = false)
    val pbProductMappingList: Set<PbProductMapping> = setOf()
) : BaseEntity()
