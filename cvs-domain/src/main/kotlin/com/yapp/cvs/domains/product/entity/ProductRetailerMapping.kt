package com.yapp.cvs.domains.product.entity

import com.yapp.cvs.common.entity.BaseTimeEntity
import com.yapp.cvs.domains.enums.RetailerType
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
) : BaseTimeEntity() {
    companion object {
        fun of(productEntity: ProductEntity, retailerType: RetailerType): ProductRetailerMapping {
            return ProductRetailerMapping(
                productId = productEntity.productId!!,
                retailerType = retailerType,
            )
        }
    }
}
