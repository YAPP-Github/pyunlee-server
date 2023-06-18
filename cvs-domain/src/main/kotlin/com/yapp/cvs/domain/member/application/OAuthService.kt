package com.yapp.cvs.domain.member.application

import com.yapp.cvs.domain.enums.MemberStatus
import com.yapp.cvs.domain.enums.NickNameRule
import com.yapp.cvs.domain.enums.OAuthLoginType
import com.yapp.cvs.domain.member.repository.MemberRepository
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class OAuthService(
    private val memberRepository: MemberRepository
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val delegate = DefaultOAuth2UserService()
        val oauth2User = delegate.loadUser(userRequest)

        val registrationId = userRequest.clientRegistration.registrationId
        val oAuthLoginType = OAuthLoginType.getByRegistrationId(registrationId)
        val attributes = oauth2User.attributes

        val member = oAuthLoginType.generateMember(attributes, generateNotDuplicatedNickName())
        if (memberRepository.findByEmailAndLoginTypeAndMemberStatus(member.email, member.loginType, MemberStatus.ACTIVATED) == null) {
            memberRepository.save(member)
        }

        // TODO: member 정보로 token 생성
        return oauth2User
    }
    private fun generateNotDuplicatedNickName(): String {
        var nickName = NickNameRule.generateNickName()
        while (memberRepository.countByNickName(nickName) > 0) {
            nickName = NickNameRule.generateNickName()
        }
        return nickName
    }
}
