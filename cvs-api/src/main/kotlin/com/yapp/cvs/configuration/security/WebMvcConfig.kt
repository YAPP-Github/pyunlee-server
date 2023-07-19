package com.yapp.cvs.configuration.security

import com.yapp.cvs.configuration.security.resolver.MemberArgumentResolver
import com.yapp.cvs.domain.member.application.JwtService
import com.yapp.cvs.domain.member.application.MemberService

import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig(
    private val jwtService: JwtService,
    private val memberService: MemberService
): WebMvcConfigurer{
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(MemberArgumentResolver(jwtService, memberService))
    }
}