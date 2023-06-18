package com.yapp.cvs.configuration.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
class SecurityConfiguration() {
    @Bean
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity
            .cors().and()
            .csrf().disable()
            .requestMatcher(AntPathRequestMatcher("/**"))
//            .authorizeRequests{auth ->
//                auth.anyRequest().authenticated()
//            }/*.authorizeRequests{auth ->
//                auth.mvcMatchers(SWAGGER_PATTERNS).permitAll()
//            }*/
            .build()
    }

    companion object {
        private val SWAGGER_PATTERNS = arrayOf("/swagger-resources/**", "/swagger-ui/**", "/v3/api-docs/**", "/v3/api-docs", "/api-docs/**").joinToString()
    }
}