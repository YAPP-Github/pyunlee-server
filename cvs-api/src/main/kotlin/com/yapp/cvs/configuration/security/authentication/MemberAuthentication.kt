package com.yapp.cvs.configuration.security.authentication

import com.yapp.cvs.domain.member.entity.TempMember
import org.springframework.security.authentication.AbstractAuthenticationToken

data class MemberAuthentication(
    val tempMember: TempMember
): AbstractAuthenticationToken(null) {
    override fun getCredentials(): Any {
        return tempMember
    }

    override fun getPrincipal(): Any? {
        return null
    }
}