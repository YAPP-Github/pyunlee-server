package com.yapp.cvs.domain.comment.entity

import com.yapp.cvs.domain.base.BaseEntity
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "product_comments")
class ProductComment(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val productCommentId: Long? = null,

        val productId: Long,

        val memberId: Long,

        var content: String,

        var valid: Boolean = true,
): BaseEntity()