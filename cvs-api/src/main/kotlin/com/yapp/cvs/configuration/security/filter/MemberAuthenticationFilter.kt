package com.yapp.cvs.configuration.security.filter

import com.yapp.cvs.domain.member.application.MemberService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class MemberAuthenticationFilter (
    private val memberService: MemberService
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = request.getHeader("X_ACCESS_TOKEN")
        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return arrayOf(
            "/api/v1/member/signup",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/v3/api-docs",
            "/api-docs/**")
            .contains(request.requestURI)
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(MemberAuthenticationFilter::class.java)
    }
}