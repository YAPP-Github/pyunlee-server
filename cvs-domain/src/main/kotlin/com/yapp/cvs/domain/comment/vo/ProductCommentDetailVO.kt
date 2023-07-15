package com.yapp.cvs.domain.comment.vo

import com.yapp.cvs.domain.enums.ProductLikeType
import com.yapp.cvs.domain.member.vo.MemberVO
import java.time.LocalDateTime

data class ProductCommentDetailVO(
        val productCommentId: Long,
        val content: String,
        val commentLikeCount: Long,
        val createdAt: LocalDateTime,
        val likeType: ProductLikeType? = ProductLikeType.NONE,
        val productId: Long,
        private val memberId: Long,
        private val nickname: String,
        var liked: Boolean? = false,
        var isOwner: Boolean? = false
) {
        val memberVO = MemberVO(memberId, nickname)
}
