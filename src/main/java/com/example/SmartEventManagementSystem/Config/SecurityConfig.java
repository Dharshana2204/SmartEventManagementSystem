//package com.example.SmartEventManagementSystem.Config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/h2-console/**").permitAll()
//                        .anyRequest().permitAll()
//                )
//                .formLogin(login -> login
//                        .disable()
//                )
//                .headers(headers -> headers
//                        .frameOptions(frame -> frame.disable())
//                )
//                .csrf(csrf -> csrf.disable());
//
//        return http.build();
//    }
//}
