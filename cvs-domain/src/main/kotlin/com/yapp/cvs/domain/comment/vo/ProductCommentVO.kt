package com.yapp.cvs.domain.comment.vo

import com.yapp.cvs.domain.comment.entity.ProductCommentRatingHistory
import com.yapp.cvs.domain.comment.view.ProductCommentView
import com.yapp.cvs.domain.enums.ProductLikeType
import com.yapp.cvs.domain.member.entity.Member
import java.time.LocalDateTime

data class ProductCommentVO(
    val productCommentId: Long,
    val memberId: Long,
    val memberNickName: String,
    val content: String,
    val likeCount: Long,
    val isOwner: Boolean,
    val liked: Boolean,
    val createdAt: LocalDateTime,
    val productId: Long,
    val productLikeType: ProductLikeType
) {
    companion object {
        fun of(productCommentView: ProductCommentView, member: Member, productCommentRatingHistory: ProductCommentRatingHistory?): ProductCommentVO {
            return ProductCommentVO(
                productCommentId = productCommentView.productComment.productCommentId!!,
                memberId = productCommentView.member.memberId!!,
                memberNickName = productCommentView.member.nickName,
                content = productCommentView.productComment.content,
                likeCount = productCommentView.likeCount ?: 0,
                isOwner = productCommentView.member.memberId == member.memberId,
                liked = productCommentRatingHistory != null,
                createdAt = productCommentView.productComment.createdAt,
                productId = productCommentView.productComment.productId,
                productLikeType = productCommentView.productLikeType ?: ProductLikeType.NONE
            )
        }
    }
}