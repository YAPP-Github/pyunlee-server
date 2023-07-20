package com.yapp.cvs.configuration.security.filter

import com.yapp.cvs.domain.member.application.JwtService
import com.yapp.cvs.domain.member.application.MemberService
import com.yapp.cvs.exception.BadRequestException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class MemberAuthenticationFilter (
    private val jwtService: JwtService
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = request.getHeader(X_ACCESS_TOKEN)
            ?: throw BadRequestException("X-ACCESS-TOKEN이 없습니다")

        jwtService.parse(token)

        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
         arrayOf(
            "/hello",
            "/api/v1/member/signup",
            "/api/v1/member/login",
            "/swagger-resources/",
            "/swagger-ui/",
            "/v3/api-docs/",
            "/v3/api-docs",
            "/api-docs/")
            .forEach {
                if(request.requestURI.contains(it)) {
                    return true
                }
            }
        return false
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(MemberAuthenticationFilter::class.java)
        const val X_ACCESS_TOKEN = "X-ACCESS-TOKEN"
    }
}