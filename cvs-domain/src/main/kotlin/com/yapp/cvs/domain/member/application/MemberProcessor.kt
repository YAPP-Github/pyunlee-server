package com.yapp.cvs.domain.member.application

import com.yapp.cvs.domain.member.application.impl.OAuthServiceFactory
import com.yapp.cvs.domain.member.entity.Member
import com.yapp.cvs.domain.member.vo.MemberAccessRequestVO
import com.yapp.cvs.exception.BadRequestException
import com.yapp.cvs.infrastructure.redis.RedisKey
import com.yapp.cvs.infrastructure.redis.RedisKeyType
import com.yapp.cvs.infrastructure.redis.service.RedisService
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class MemberProcessor(
    private val oAuthServiceFactory: OAuthServiceFactory,
    private val memberService: MemberService,
    private val jwtService: JwtService,
    private val redisService: RedisService
) {
    fun signUp(accessRequestVO: MemberAccessRequestVO): String {
        val oauthMember = oAuthServiceFactory.getOauthService(accessRequestVO.loginType)
            .authorize(accessRequestVO.idToken)

        if (memberService.findMemberByOAuthMember(oauthMember) != null) {
            throw BadRequestException("이미 가입된 회원입니다")
        }

        val member = memberService.signUp(oauthMember)

        return generateMemberAccessToken(member)
    }

    fun login(accessRequestVO: MemberAccessRequestVO): String {
        val oauthMember = oAuthServiceFactory.getOauthService(accessRequestVO.loginType)
            .authorize(accessRequestVO.idToken)

        val member = memberService.findMemberByOAuthMember(oauthMember)
            ?: throw BadRequestException("이미 가입된 회원입니다")

        return redisService.getMap(RedisKey.createKey(RedisKeyType.MEMBER_ACCESS_TOKEN), member.memberId!!)
            ?: generateMemberAccessToken(member)
    }

    private fun generateMemberAccessToken(member: Member): String {
        val token = jwtService.generate(member)
        redisService.putMap(RedisKey.createKey(RedisKeyType.MEMBER_ACCESS_TOKEN), member.memberId!!, token)
        return token
    }
}