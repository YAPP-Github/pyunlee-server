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
        fun like(memberProductMappingKey: MemberProductMappingKey): ProductLikeHistory {
            return ProductLikeHistory(
                productId = memberProductMappingKey.productId,
                memberId = memberProductMappingKey.memberId,
                likeType = ProductLikeType.LIKE
            )
        }

        fun dislike(memberProductMappingKey: MemberProductMappingKey): ProductLikeHistory {
            return ProductLikeHistory(
                productId = memberProductMappingKey.productId,
                memberId = memberProductMappingKey.memberId,
                likeType = ProductLikeType.DISLIKE
            )
        }

        fun none(memberProductMappingKey: MemberProductMappingKey): ProductLikeHistory {
            return ProductLikeHistory(
                productId = memberProductMappingKey.productId,
                memberId = memberProductMappingKey.memberId,
                likeType = ProductLikeType.NONE
            )
        }
    }
}
