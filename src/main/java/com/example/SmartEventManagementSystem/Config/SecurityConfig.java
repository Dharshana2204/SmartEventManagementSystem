package com.example.SmartEventManagementSystem.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Disable CSRF protection (useful for API testing with tools like Postman)
        http.csrf(csrf -> csrf.disable())
            // Allow all requests during development/testing so endpoints aren't blocked by Spring Security defaults
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

        // return the built security chain
        return http.build();
    }
}
