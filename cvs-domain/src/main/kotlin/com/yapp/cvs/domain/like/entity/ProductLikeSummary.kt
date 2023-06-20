package com.yapp.cvs.domain.like.entity

import com.yapp.cvs.domain.base.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "product_like_summaries")
data class ProductLikeSummary(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val productLikeSummaryId: Long? = null,

    val productId: Long,

    val likeCount: Long,

    val totalCount: Long,
) : BaseEntity()
