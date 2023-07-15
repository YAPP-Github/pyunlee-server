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
@Table(name = "product_comment_like_summaries")
class ProductCommentLikeSummary(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val productCommentLikeSummaryId: Long? = null,

        val productId: Long,

        val memberId: Long,

        var likeCount: Long
): BaseEntity() {
    fun like() {
        likeCount += 1
    }

    fun cancelLike() {
        likeCount = max(likeCount - 1, 0)
    }

    companion object {
        fun empty(memberProductMappingKey: MemberProductMappingKey): ProductCommentLikeSummary {
            return ProductCommentLikeSummary(
                    productId = memberProductMappingKey.productId,
                    memberId = memberProductMappingKey.memberId,
                    likeCount = 0
            )
        }
    }
}
