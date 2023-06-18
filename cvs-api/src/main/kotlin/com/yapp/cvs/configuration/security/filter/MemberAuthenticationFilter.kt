package com.yapp.cvs.configuration.security.filter

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class MemberAuthenticationFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // val memberAuthentication =  MemberAuthentication().apply { isAuthenticated = true }
        // SecurityContextHolder.getContext().authentication = memberAuthentication

        filterChain.doFilter(request, response)
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(MemberAuthenticationFilter::class.java)
    }
}
