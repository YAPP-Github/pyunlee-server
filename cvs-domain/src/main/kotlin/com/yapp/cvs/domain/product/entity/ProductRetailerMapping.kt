package com.yapp.cvs.domain.product.entity

import com.yapp.cvs.domain.base.BaseEntity
import com.yapp.cvs.domain.enums.RetailerType
import javax.persistence.*

@Entity
@Table(name = "product_retailer_mappings")
class ProductRetailerMapping(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val productRetailerMappingId: Long? = null,

    val productId: Long,

    @Enumerated(EnumType.STRING)
    val retailerType: RetailerType
) : BaseEntity() {
    companion object {
        fun of(product: Product, retailerType: RetailerType): ProductRetailerMapping {
            return ProductRetailerMapping(
                productId = product.productId!!,
                retailerType = retailerType,
            )
        }
    }
}
