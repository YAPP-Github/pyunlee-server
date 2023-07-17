package com.yapp.cvs.domain.comment.vo

import com.yapp.cvs.domain.comment.view.ProductCommentDetailView
import com.yapp.cvs.domain.enums.ProductLikeType
import com.yapp.cvs.domain.member.entity.Member
import java.time.LocalDateTime

data class ProductCommentDetailVO(
    val productCommentId: Long,
    val memberId: Long,
    val memberNickName: String,
    val content: String,
    val likeCount: Long,
    val createdAt: LocalDateTime,
    val productId: Long,
    val productName: String,
    val productBrandName: String,
    val productImageUrl: String?,
    val productLikeType: ProductLikeType
) {
    companion object {
        fun of(productCommentDetailView: ProductCommentDetailView, member: Member): ProductCommentDetailVO {
            return ProductCommentDetailVO(
                productCommentId = productCommentDetailView.productComment.productCommentId!!,
                memberId = member.memberId!!,
                memberNickName = member.nickName,
                content = productCommentDetailView.productComment.content,
                likeCount = productCommentDetailView.likeCount ?: 0,
                createdAt = productCommentDetailView.productComment.createdAt,
                productId = productCommentDetailView.product.productId!!,
                productName = productCommentDetailView.product.productName,
                productBrandName = productCommentDetailView.product.brandName,
                productImageUrl = productCommentDetailView.product.imageUrl,
                productLikeType = productCommentDetailView.productLikeType ?: ProductLikeType.NONE
            )
        }
    }
}
