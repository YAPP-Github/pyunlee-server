package com.yapp.cvs.domain.like.entity

import com.yapp.cvs.domain.base.BaseEntity
import com.yapp.cvs.domain.enums.ProductLikeType
import javax.persistence.*

@Entity
@Table(name = "product_like_histories")
data class ProductLikeHistory(
        @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val productLikeHistoryId: Long? = null,

    val productId: Long,

    val memberId: Long,

    @Enumerated(EnumType.STRING)
    val likeType: ProductLikeType = ProductLikeType.NONE,
) : BaseEntity() {
    companion object {
        fun of(productId: Long, memberId: Long, likeType: ProductLikeType): ProductLikeHistory {
            return ProductLikeHistory(
                    productId = productId,
                    memberId = memberId,
                    likeType = likeType
            )
        }
    }
}
