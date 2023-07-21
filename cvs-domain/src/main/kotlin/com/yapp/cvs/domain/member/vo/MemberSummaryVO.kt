package com.yapp.cvs.domain.member.vo

import com.yapp.cvs.domain.member.entity.Member

data class MemberSummaryVO(
        val memberId: Long,
        val nickname: String,
        val productLikeCount: Long,
        val productCommentCount: Long
) {
    companion object {
        fun of(member: Member, productLikeCount: Long, productCommentCount: Long): MemberSummaryVO {
            return MemberSummaryVO(
                    memberId = member.memberId!!,
                    nickname = member.nickName,
                    productLikeCount = productLikeCount,
                    productCommentCount = productCommentCount
            )
        }
    }
}