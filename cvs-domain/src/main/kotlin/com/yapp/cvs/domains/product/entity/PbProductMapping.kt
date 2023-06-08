package com.yapp.cvs.domains.product.entity

import com.yapp.cvs.common.entity.BaseTimeEntity
import javax.persistence.*

@Entity
@Table(name = "pb_product_mappings")
class PbProductMapping(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val pbProductMappingId: Long? = null,

    val productId: Long,
) : BaseTimeEntity() {
    companion object {
        fun of(productEntity: ProductEntity): PbProductMapping {
            return PbProductMapping(
                productId = productEntity.productId!!
            )
        }
    }
}
