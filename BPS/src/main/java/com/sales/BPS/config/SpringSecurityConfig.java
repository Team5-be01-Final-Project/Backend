package com.sales.BPS.config;

import com.sales.BPS.msystem.service.CustomUserDetailsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder; // PasswordEncoder를 필드로 선언

    // CustomUserDetailsService를 생성자 주입 방식으로 받음
    // CustomUserDetailsService와 PasswordEncoder를 생성자 주입
    public SpringSecurityConfig(@Lazy CustomUserDetailsService customUserDetailsService, @Lazy PasswordEncoder passwordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder; // 필드에 주입
    }

    // PasswordEncoder 빈 정의
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // SecurityFilterChain 빈 정의
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
                .authorizeRequests(auth -> auth
                        .requestMatchers("/resources/**", "/static/**", "/css/**", "/js/**").permitAll() // 정적 리소스에 대한 접근 허용
                        .requestMatchers("/api/login", "/api/login-error").permitAll() // 특정 경로에 대해 접근 허용
                        .requestMatchers("/api/admin/**").hasAuthority("ADMIN") // ADMIN 권한 필요
                        .requestMatchers("/api/manager/**").hasAuthority("MANAGER") // MANAGER 권한 필요
                        .requestMatchers("/api/employee/**").hasAuthority("EMPLOYEE") // EMPLOYEE 권한 필요
                        .anyRequest().authenticated() // 나머지 경로는 인증 필요
                )
                .formLogin() // 기본 로그인 폼 활성화
                .and()
                .exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() { // 접근 거부 처리자 정의
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response,
                                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                    }
                });
        // 필요한 추가 설정을 여기에 추가

        return http.build();
    }

    // AuthenticationManagerBuilder 구성을 위한 메서드
    // @Autowired 어노테이션 사용
    // configureGlobal 메서드 수정
    // @Autowired 어노테이션을 제거하고, 빈 생성 메서드가 아님을 명확히 합니다.
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder); // 필드 주입된 PasswordEncoder 사용
    }
}
