package com.yapp.cvs.domains.product.entity

import com.yapp.cvs.common.entity.BaseTimeEntity
import com.yapp.cvs.domains.enums.ProductCategoryType
import javax.persistence.*

@Entity
@Table(name = "products")
// TODO: class name change to Product after merge all progress
data class ProductEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val productId: Long? = null,

    val productName: String,

    val brandName: String,

    val price: Long,

    val productCategoryType: ProductCategoryType,

    val barcode: String,

    val imageUrl: String,

    val valid: Boolean = true
): BaseTimeEntity() {
}