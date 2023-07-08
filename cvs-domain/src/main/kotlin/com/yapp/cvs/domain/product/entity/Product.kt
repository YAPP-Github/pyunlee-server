package com.yapp.cvs.domain.product.entity

import com.yapp.cvs.domain.base.BaseEntity
import com.yapp.cvs.domain.enums.ProductCategoryType
import com.yapp.cvs.domain.product.vo.ProductUpdateVO
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

    var productName: String,

    var brandName: String,

    var price: Long,

    @Enumerated(EnumType.STRING)
    var productCategoryType: ProductCategoryType,

    val barcode: String,

    val imageUrl: String? = null,

    var valid: Boolean = true,

    @OneToMany
    @JoinColumn(name = "productId", insertable = false, updatable = false)
    val productPromotionList: Set<ProductPromotion> = setOf(),

    @OneToMany
    @JoinColumn(name = "productId", insertable = false, updatable = false)
    val pbProductMappingList: Set<PbProductMapping> = setOf()
) : BaseEntity() {
    fun update(productUpdateVO: ProductUpdateVO) {
        productName = productUpdateVO.productName
        brandName = productUpdateVO.brandName
        price = productUpdateVO.price
        productCategoryType = productUpdateVO.productCategoryType
    }
}
