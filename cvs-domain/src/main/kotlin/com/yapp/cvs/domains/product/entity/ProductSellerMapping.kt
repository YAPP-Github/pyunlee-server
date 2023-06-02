package com.yapp.cvs.domains.product.entity

import com.yapp.cvs.common.entity.BaseTimeEntity
import com.yapp.cvs.domains.enums.SellerType
import javax.persistence.*

@Entity
@Table(name = "product_seller_mappings")
class ProductSellerMapping(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val productSellerMappingId: Long? = null,

    val productId: Long,

    @Enumerated(EnumType.STRING)
    val sellerType: SellerType
): BaseTimeEntity() {
}