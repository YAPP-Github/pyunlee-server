package com.yapp.cvs.domain.member.application.impl

import com.yapp.cvs.domain.enums.OAuthLoginType
import com.yapp.cvs.domain.enums.OAuthMember
import com.yapp.cvs.domain.member.application.OAuthService
import com.yapp.cvs.exception.BadRequestException
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class GoogleOAuthService(
): OAuthService {
    override fun getLoginType(): OAuthLoginType {
        return OAuthLoginType.GOOGLE
    }

    override fun authorize(idToken: String): OAuthMember {
        val response = RestTemplate().exchange(
            AUTHORIZATION_URL,
            HttpMethod.GET,
            null,
            object : ParameterizedTypeReference<Map<String, Any?>>() {},
            idToken)

        if(response.body == null) {
            throw BadRequestException("ID_TOKEN을 인증 할 수 없습니다.")
        }

        return OAuthMember.google(response.body!!)
    }
    companion object {
        const val AUTHORIZATION_URL = "https://oauth2.googleapis.com/tokeninfo?id_token={idToken}"
    }
}