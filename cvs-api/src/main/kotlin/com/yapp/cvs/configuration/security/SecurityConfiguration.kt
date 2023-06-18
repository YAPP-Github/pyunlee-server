package com.yapp.cvs.configuration.security

import com.yapp.cvs.domain.member.application.OAuthService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val oAuthService: OAuthService
) {
    @Bean
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity
            .cors().and().csrf().disable()
            .oauth2Login().userInfoEndpoint().userService(oAuthService).and().and()
            // .requestMatcher(AntPathRequestMatcher("/**"))
            .build()
    }

    companion object {
        private val SWAGGER_PATTERNS = arrayOf("/swagger-resources/**", "/swagger-ui/**", "/v3/api-docs/**", "/v3/api-docs", "/api-docs/**").joinToString()
    }
}
