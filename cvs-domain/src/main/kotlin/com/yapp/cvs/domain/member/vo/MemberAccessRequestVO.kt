package com.yapp.cvs.domain.member.vo

import com.yapp.cvs.domain.enums.OAuthLoginType

class MemberAccessRequestVO(
    val idToken: String,
    val loginType: OAuthLoginType
) {
}