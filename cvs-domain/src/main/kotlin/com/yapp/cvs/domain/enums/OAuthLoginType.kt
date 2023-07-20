package com.yapp.cvs.domain.enums

import com.yapp.cvs.exception.BadRequestException


enum class OAuthLoginType(
): MemberParser {
    GOOGLE {
        override fun parseMember(attr: Map<String, Any?>): OAuthMember {
            return OAuthMember(
                email = attr["email"]?.toString() ?: throw BadRequestException("ID_TOKEN을 인증 할 수 없습니다."),
                loginType = GOOGLE
            )
        }
    },
    KAKAO {
        override fun parseMember(attr: Map<String, Any?>): OAuthMember {
            return OAuthMember(
                email = attr["email"]?.toString() ?: throw BadRequestException("ID_TOKEN을 인증 할 수 없습니다."),
                loginType = KAKAO
            )
        }
    }
    ;
}

data class OAuthMember(
    val email: String,
    val loginType: OAuthLoginType,
){
}

interface MemberParser {
    fun parseMember(attr: Map<String, Any?>): OAuthMember
}