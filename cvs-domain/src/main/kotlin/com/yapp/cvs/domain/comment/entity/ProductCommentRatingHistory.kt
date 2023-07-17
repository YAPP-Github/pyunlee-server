package com.yapp.cvs.domain.comment.entity

import com.yapp.cvs.domain.base.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "product_comment_rating_histories")
class ProductCommentRatingHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val productCommentRatingHistoryId: Long? = null,

    val productCommentId: Long,

    val memberId: Long,

    @Enumerated(EnumType.STRING)
    val productCommentRatingType: ProductCommentRatingType,

    var valid: Boolean = true
): BaseEntity() {
    companion object {
        fun like(memberId: Long, productCommentId: Long): ProductCommentRatingHistory {
            return ProductCommentRatingHistory(
                productCommentId = productCommentId,
                memberId = memberId,
                productCommentRatingType = ProductCommentRatingType.LIKE
            )
        }
    }
}

enum class ProductCommentRatingType{
    LIKE,
    DISLIKE
}