package com.yapp.cvs.domain.enums

import com.yapp.cvs.domain.member.entity.TempMember

enum class OAuthLoginType(
    val registrationId: String,
    val create: (Map<String, Any>) -> TempMember
) {
    GOOGLE("google", {TempMember.google(it)}),
    ;

    companion object {
        fun getByRegistrationId(registrationId: String): OAuthLoginType {
            return values().first { it.registrationId == registrationId }
        }
    }
}