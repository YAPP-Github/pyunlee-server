package com.yapp.cvs.domain.member.application.impl

import com.yapp.cvs.domain.enums.OAuthLoginType
import com.yapp.cvs.domain.member.application.OAuthService
import org.springframework.stereotype.Component

@Component
class OAuthServiceFactory(
    private val oAuthServiceList: List<OAuthService>,
) {
    private val oauthServiceMap: MutableMap<OAuthLoginType, OAuthService> = mutableMapOf()
    init {
        oAuthServiceList.forEach {
            oauthServiceMap[it.getLoginType()] = it
        }
    }

    fun getOauthService(loginType: OAuthLoginType): OAuthService {
        return oauthServiceMap[loginType] ?: throw RuntimeException("서비스 불가능한 로그인 타입")
    }
}