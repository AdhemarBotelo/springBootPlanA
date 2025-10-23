package com.adhemar.userManagement.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() } // disable CSRF for testing with Postman
            .authorizeHttpRequests { auth ->
                auth.anyRequest().permitAll() // allow all requests (no login required)
            }
        return http.build()
    }
}