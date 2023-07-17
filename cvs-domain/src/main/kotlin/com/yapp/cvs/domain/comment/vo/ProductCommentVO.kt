package com.yapp.cvs.domain.comment.vo

import com.yapp.cvs.domain.comment.view.ProductCommentView
import com.yapp.cvs.domain.member.entity.Member
import java.time.LocalDateTime

data class ProductCommentVO(
    val productCommentId: Long,
    val memberId: Long,
    val memberNickName: String,
    val content: String,
    val likeCount: Long,
    val createdAt: LocalDateTime,
    val productId: Long
) {
    companion object {
        fun of(productCommentView: ProductCommentView, member: Member): ProductCommentVO {
            return ProductCommentVO(
                productCommentId = productCommentView.productComment.productCommentId!!,
                memberId = member.memberId!!,
                memberNickName = member.nickName,
                content = productCommentView.productComment.content,
                likeCount = productCommentView.likeCount ?: 0,
                createdAt = productCommentView.productComment.createdAt,
                productId = productCommentView.productComment.productId
            )
        }
    }
}