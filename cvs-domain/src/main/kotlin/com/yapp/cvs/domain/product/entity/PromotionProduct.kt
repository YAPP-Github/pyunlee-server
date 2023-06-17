package com.yapp.cvs.domain.product.entity

import com.yapp.cvs.domain.base.BaseEntity
import com.yapp.cvs.domain.enums.RetailerType
import javax.persistence.*

@Entity
@Table(name = "promotion_products")
class PromotionProduct(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val promotionProductId: Long? = null,

    val productId: Long,

    @Enumerated(EnumType.STRING)
    val promotionType: ProductPromotionType,

    @Enumerated(EnumType.STRING)
    val retailerType: RetailerType,

) : BaseEntity()
