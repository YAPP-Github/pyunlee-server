package com.yapp.cvs.configuration.security.authentication

import com.yapp.cvs.domain.member.entity.Member
import org.springframework.security.authentication.AbstractAuthenticationToken

data class MemberAuthentication(
    val member: Member
) : AbstractAuthenticationToken(null) {
    override fun getCredentials(): Any {
        return member
    }

    override fun getPrincipal(): Any? {
        return null
    }
}
