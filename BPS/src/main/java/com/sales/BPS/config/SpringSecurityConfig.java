//package com.sales.BPS.config;
//
//import com.sales.BPS.msystem.service.CustomUserDetailsService;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.access.AccessDeniedHandler;
//
//import java.io.IOException;
//
//@Configuration
//@EnableWebSecurity
//public class SpringSecurityConfig {
//
//    private final CustomUserDetailsService customUserDetailsService;
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }
//
//    // CustomUserDetailsService를 생성자 주입 방식으로 받음
//    public SpringSecurityConfig(@Lazy CustomUserDetailsService customUserDetailsService) {
//        this.customUserDetailsService = customUserDetailsService;
//    }
//
//    // SecurityFilterChain 빈 정의
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
//                .authorizeRequests(auth -> auth
//                        .requestMatchers("/").permitAll() // 전체 경로에 대한 접근 허용
////                        .requestMatchers("/resources/**", "/static/**", "/css/**", "/js/**").permitAll() // 정적 리소스에 대한 접근 허용
////                        .requestMatchers("/api/login", "/api/login-error").permitAll() // 특정 경로에 대해 접근 허용(
////                        .requestMatchers("/api/admin/**").hasAuthority("ITADMIN") // ITADMIN 권한 필요
////                        .requestMatchers("/api/admin/**").hasAuthority("ADMIN") // ADMIN 권한 필요
////                        .requestMatchers("/api/manager/**").hasAuthority("MANAGER") // MANAGER 권한 필요
////                        .requestMatchers("/api/employee/**").hasAuthority("EMPLOYEE") // EMPLOYEE 권한 필요
////                        .anyRequest().authenticated() // 나머지 경로는 인증 필요
//                );
//
//
//        return http.build();
//    }
//
//    // AuthenticationManagerBuilder 구성을 위한 메서드
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customUserDetailsService);
//    }
//}
