package com.yapp.cvs.config.security

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
class SecurityConfig : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.formLogin().disable().cors().and().csrf().disable()
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http.authorizeRequests().mvcMatchers(SWAGGER_PATTERNS).permitAll()
        return http.build()
    }

    companion object {
        private val SWAGGER_PATTERNS = arrayOf("/swagger-resources/**", "/swagger-ui/**", "/v3/api-docs/**", "/v3/api-docs", "/api-docs/**").joinToString()
    }
}
