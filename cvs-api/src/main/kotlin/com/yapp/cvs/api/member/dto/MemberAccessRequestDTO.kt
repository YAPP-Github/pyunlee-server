package com.yapp.cvs.api.member.dto

import com.yapp.cvs.domain.enums.OAuthLoginType
import com.yapp.cvs.domain.member.vo.MemberAccessRequestVO

class MemberAccessRequestDTO(
    val idToken: String,
    val loginType: String
) {
    fun toVO(): MemberAccessRequestVO {
        return MemberAccessRequestVO(
            idToken = idToken,
            loginType = OAuthLoginType.valueOf(loginType)
        )
    }
}