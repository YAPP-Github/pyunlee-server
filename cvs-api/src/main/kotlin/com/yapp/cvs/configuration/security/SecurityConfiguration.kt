package com.yapp.cvs.configuration.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfiguration {
    @Bean
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity
            .cors().and()
            .csrf().disable()
            .authorizeRequests { auth ->
                auth.mvcMatchers(SWAGGER_PATTERNS).permitAll()
            }
            .build()
    }

    companion object {
        private val SWAGGER_PATTERNS = arrayOf("/swagger-resources/**", "/swagger-ui/**", "/v3/api-docs/**", "/v3/api-docs", "/api-docs/**").joinToString()
    }
}
