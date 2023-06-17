package com.yapp.cvs.domain.member.entity

import com.yapp.cvs.domain.enums.MemberStatus
import com.yapp.cvs.domain.enums.OAuthLoginType

data class TempMember(
    val email: String,
    val loginType: OAuthLoginType,
) {
    fun to(): Member{
        return Member(
            email = email,
            loginType = loginType,
            memberStatus = MemberStatus.ACTIVATED
        )
    }
    companion object {
        fun google(attributes: Map<String, Any>): TempMember{
            return TempMember(
                email = attributes["email"].toString(),
                loginType = OAuthLoginType.GOOGLE
            )
        }
    }
}