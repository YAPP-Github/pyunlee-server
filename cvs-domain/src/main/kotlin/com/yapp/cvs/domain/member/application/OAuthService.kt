package com.yapp.cvs.domain.member.application

import com.yapp.cvs.domain.enums.OAuthLoginType
import com.yapp.cvs.domain.enums.OAuthMember

interface OAuthService {
    fun getLoginType(): OAuthLoginType
    fun authorize(idToken: String): OAuthMember
}