package com.yapp.cvs.domain.member.application

import com.yapp.cvs.domain.comment.application.ProductCommentService
import com.yapp.cvs.domain.like.application.MemberProductLikeMappingService
import com.yapp.cvs.domain.member.application.impl.OAuthServiceFactory
import com.yapp.cvs.domain.member.entity.Member
import com.yapp.cvs.domain.member.vo.MemberAccessRequestVO
import com.yapp.cvs.domain.member.vo.MemberSummaryVO
import com.yapp.cvs.domain.member.vo.MemberUpdateVO
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
    private val redisService: RedisService,
    private val productCommentService: ProductCommentService,
    private val memberProductLikeMappingService: MemberProductLikeMappingService
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
            ?: throw BadRequestException("가입되지 않은 회원입니다.")

        return redisService.getMap(RedisKey.createKey(RedisKeyType.MEMBER_ACCESS_TOKEN), member.memberId!!)
            ?: generateMemberAccessToken(member)
    }

    fun getMemberSummary(member: Member): MemberSummaryVO {
        val productLikeCount = memberProductLikeMappingService.countByMemberId(member.memberId!!)
        val productCommentCount = productCommentService.countTotalCommentByMember(member.memberId!!)
        return MemberSummaryVO.of(member = member, productLikeCount = productLikeCount, productCommentCount = productCommentCount)
    }

    fun update(member: Member, memberUpdateVO: MemberUpdateVO) {
        memberService.update(member.memberId!!, memberUpdateVO)
    }

    private fun generateMemberAccessToken(member: Member): String {
        val token = jwtService.generate(member)
        redisService.putMap(RedisKey.createKey(RedisKeyType.MEMBER_ACCESS_TOKEN), member.memberId!!, token)
        return token
    }
}