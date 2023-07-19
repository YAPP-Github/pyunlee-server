package com.yapp.cvs.configuration.security.resolver

import com.yapp.cvs.configuration.security.filter.MemberAuthenticationFilter.Companion.X_ACCESS_TOKEN
import com.yapp.cvs.domain.member.application.JwtService
import com.yapp.cvs.domain.member.application.MemberService
import com.yapp.cvs.domain.member.entity.Member
import com.yapp.cvs.exception.BadTokenException
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import javax.servlet.http.HttpServletRequest

class MemberArgumentResolver(
    private val jwtService: JwtService,
    private val memberService: MemberService
): HandlerMethodArgumentResolver{
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.parameterType == Member::class.java
    }

    override fun resolveArgument(parameter: MethodParameter, mavContainer: ModelAndViewContainer?, webRequest: NativeWebRequest, binderFactory: WebDataBinderFactory?): Any? {
        val httpRequest: HttpServletRequest = webRequest.nativeRequest as HttpServletRequest

        val token = httpRequest.getHeader(X_ACCESS_TOKEN)
            ?: throw BadTokenException("토큰 정보가 없습니다")

        val memberId = jwtService.parse(token)
        return memberService.findMemberById(memberId)
    }
}