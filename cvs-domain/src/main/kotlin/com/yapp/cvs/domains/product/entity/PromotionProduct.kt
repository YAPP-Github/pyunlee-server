package com.yapp.cvs.domains.product.entity

import com.yapp.cvs.common.entity.BaseTimeEntity
import com.yapp.cvs.domains.enums.RetailerType
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

) : BaseTimeEntity()
