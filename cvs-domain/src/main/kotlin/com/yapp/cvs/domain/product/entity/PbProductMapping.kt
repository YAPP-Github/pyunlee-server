package com.yapp.cvs.domain.product.entity

import com.yapp.cvs.domain.base.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "pb_product_mappings")
class PbProductMapping(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val pbProductMappingId: Long? = null,

    val productId: Long,
) : BaseEntity() {
    companion object {
        fun of(product: Product): PbProductMapping {
            return PbProductMapping(
                productId = product.productId!!,
            )
        }
    }
}
