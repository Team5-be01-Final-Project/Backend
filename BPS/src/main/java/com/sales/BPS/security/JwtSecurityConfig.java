//package com.sales.BPS.security;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@RequiredArgsConstructor
//public class JwtSecurityConfig {
//    private final TokenProvider tokenProvider;
//
//    @Override
//    public void configure(HttpSecurity http) {
//
//        // security 로직에 JwtFilter 등록
//        http.addFilterBefore(
//                new JwtFilter(tokenProvider),
//                UsernamePasswordAuthenticationFilter.class
//        );
//    }
//}
