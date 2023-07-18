package com.yapp.cvs.domain.comment.entity

import com.yapp.cvs.domain.base.BaseEntity
import com.yapp.cvs.domain.like.entity.MemberProductMappingKey
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import kotlin.math.max

@Entity
@Table(name = "product_comment_rating_summaries")
class ProductCommentRatingSummary(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val productCommentRatingSummaryId: Long? = null,

    val productCommentId: Long,

    var totalCount: Long,

    var likeCount: Long
) : BaseEntity() {
    fun like() {
        likeCount += 1
        totalCount += 1
    }

    fun cancelLike() {
        totalCount = max(totalCount - 1, 0)
        likeCount = max(likeCount - 1, 0)
    }

    companion object {
        fun empty(productCommentId: Long): ProductCommentRatingSummary {
            return ProductCommentRatingSummary(
                productCommentId = productCommentId,
                totalCount = 0,
                likeCount = 0
            )
        }
    }
}
