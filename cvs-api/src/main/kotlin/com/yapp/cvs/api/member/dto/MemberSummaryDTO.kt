package com.yapp.cvs.api.member.dto

import com.yapp.cvs.domain.member.vo.MemberSummaryVO

data class MemberSummaryDTO (
    val memberId: Long,
    val nickname: String,
    val productLikeCount: Long,
    val productCommentCount: Long
) {
    companion object {
        fun from(memberSummaryVO: MemberSummaryVO): MemberSummaryDTO {
            return MemberSummaryDTO(
                    memberId = memberSummaryVO.memberId,
                    nickname = memberSummaryVO.nickname,
                    productLikeCount = memberSummaryVO.productLikeCount,
                    productCommentCount = memberSummaryVO.productCommentCount
            )
        }
    }
}