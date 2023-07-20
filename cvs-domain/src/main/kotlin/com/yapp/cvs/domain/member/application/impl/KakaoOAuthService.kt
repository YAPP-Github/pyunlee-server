package com.yapp.cvs.domain.member.application.impl

import com.yapp.cvs.domain.enums.OAuthLoginType
import com.yapp.cvs.domain.enums.OAuthMember
import com.yapp.cvs.domain.member.application.OAuthService
import com.yapp.cvs.exception.BadRequestException
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate

@Service
class KakaoOAuthService(
): OAuthService {
    override fun getLoginType(): OAuthLoginType {
        return OAuthLoginType.KAKAO
    }

    override fun authorize(idToken: String): OAuthMember {
        val httpHeaders = HttpHeaders()
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")

        val httpEntity = HttpEntity(
            LinkedMultiValueMap<String?, String?>().apply { setAll(mapOf("id_token" to idToken)) },
            httpHeaders
        )

        val response = RestTemplate().exchange(
            AUTHORIZATION_URL,
            HttpMethod.POST,
            httpEntity,
            object : ParameterizedTypeReference<Map<String, Any?>>() {},
            idToken)

        if(response.body == null) {
            throw BadRequestException("ID_TOKEN을 인증 할 수 없습니다.")
        }

        return getLoginType().parseMember(response.body!!)
    }
    companion object {
        const val AUTHORIZATION_URL = "https://kauth.kakao.com/oauth/tokeninfo"
    }
}