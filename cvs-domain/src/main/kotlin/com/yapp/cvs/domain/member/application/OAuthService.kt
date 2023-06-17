package com.yapp.cvs.domain.member.application

import com.yapp.cvs.domain.enums.OAuthLoginType
import com.yapp.cvs.domain.member.repository.MemberRepository
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class OAuthService (
    private val memberRepository: MemberRepository
): OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val delegate = DefaultOAuth2UserService()
        val oauth2User = delegate.loadUser(userRequest)

        // oauth 이름
        val registrationId = userRequest.clientRegistration.registrationId
        val oAuthLoginType = OAuthLoginType.getByRegistrationId(registrationId)
        val attributes = oauth2User.attributes

        val tempMember = oAuthLoginType.create(attributes)
        val member = memberRepository.findByEmailAndLoginType(tempMember.email, tempMember.loginType)
            ?: memberRepository.save(tempMember.to())

        // TODO: member 정보로 token 생성
        return oauth2User
    }
}