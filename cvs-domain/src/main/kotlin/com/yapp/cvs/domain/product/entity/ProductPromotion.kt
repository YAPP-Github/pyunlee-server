package com.yapp.cvs.domain.product.entity

import com.yapp.cvs.domain.base.BaseEntity
import com.yapp.cvs.domain.enums.RetailerType
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "product_promotions")
class ProductPromotion(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val productPromotionId: Long? = null,

    val productId: Long,

    @Enumerated(EnumType.STRING)
    val promotionType: ProductPromotionType,

    @Enumerated(EnumType.STRING)
    val retailerType: RetailerType,

    val validAt: LocalDateTime

    ) : BaseEntity()
