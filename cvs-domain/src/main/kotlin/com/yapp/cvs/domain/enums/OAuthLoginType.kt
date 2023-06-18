package com.yapp.cvs.domain.enums

import com.yapp.cvs.domain.member.entity.Member

enum class OAuthLoginType(
    val registrationId: String,
): MemberGenerator {
    GOOGLE("google") {
        override fun generateMember(attr: Map<String, Any>, nickName: String): Member {
            return Member.google(attr, nickName)
        }
    },
    ;

    companion object {
        fun getByRegistrationId(registrationId: String): OAuthLoginType {
            return values().first { it.registrationId == registrationId }
        }
    }
}

interface MemberGenerator {
    fun generateMember(attr: Map<String, Any>, nickName: String): Member
}