package com.yapp.cvs.domain.member.application

import com.yapp.cvs.domain.enums.MemberStatus
import com.yapp.cvs.domain.enums.NickNameRule
import com.yapp.cvs.domain.enums.OAuthMember
import com.yapp.cvs.domain.member.entity.Member
import com.yapp.cvs.domain.member.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {
    fun findMemberByOAuthMember(oAuthMember: OAuthMember): Member? {
        return memberRepository.findByEmailAndLoginTypeAndMemberStatus(oAuthMember.email, oAuthMember.loginType, MemberStatus.ACTIVATED)
    }

    fun signUp(oAuthMember: OAuthMember): Member {
        return memberRepository.save(Member.create(oAuthMember, generateNotDuplicatedNickName()))
    }

    fun generateNotDuplicatedNickName(): String {
        var nickName = NickNameRule.generateNickName()
        while (memberRepository.countByNickName(nickName) > 0) {
            nickName = NickNameRule.generateNickName()
        }
        return nickName
    }
}