package com.yapp.cvs.domain.comment.entity

import com.yapp.cvs.domain.base.BaseEntity
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "product_comment_histories")
class ProductCommentHistory(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val productCommentHistoryId: Long? = null,

        val productId: Long,

        val memberId: Long,

        val content: String
): BaseEntity() {
}