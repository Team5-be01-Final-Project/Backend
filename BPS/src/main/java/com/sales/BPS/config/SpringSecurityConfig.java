package com.sales.BPS.config;

import com.sales.BPS.jwt.LoginFilter;
import com.sales.BPS.msystem.service.CustomUserDetailsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

//    private final CustomUserDetailsService customUserDetailsService;

    //AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
    private final AuthenticationConfiguration authenticationConfiguration;

    //    // CustomUserDetailsService를 생성자 주입 방식으로 받음
//    public SpringSecurityConfig(@Lazy CustomUserDetailsService customUserDetailsService) {
//        this.customUserDetailsService = customUserDetailsService;
//    }


    public SpringSecurityConfig(AuthenticationConfiguration authenticationConfiguration) {
        this.authenticationConfiguration = authenticationConfiguration;
    }

    //AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    // AuthenticationManagerBuilder 구성을 위한 메서드
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customUserDetailsService);
//    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }


    // SecurityFilterChain 빈 정의
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()); // CSRF 보호 비활성화

        http        //From 로그인 방식 disable
                .formLogin((auth) -> auth.disable());

        http				//http basic 인증 방식 disable
                .httpBasic((auth) -> auth.disable());

        http				//경로별 인가 작업
                .authorizeRequests(auth -> auth
                        .requestMatchers("/").permitAll() // 전체 경로에 대한 접근 허용
//                        .requestMatchers("/resources/**", "/static/**", "/css/**", "/js/**").permitAll() // 정적 리소스에 대한 접근 허용
//                        .requestMatchers("/api/login", "/api/login-error").permitAll() // 특정 경로에 대해 접근 허용(
//                        .requestMatchers("/api/admin/**").hasAuthority("ITADMIN") // ITADMIN 권한 필요
//                        .requestMatchers("/api/admin/**").hasAuthority("ADMIN") // ADMIN 권한 필요
//                        .requestMatchers("/api/manager/**").hasAuthority("MANAGER") // MANAGER 권한 필요
//                        .requestMatchers("/api/employee/**").hasAuthority("EMPLOYEE") // EMPLOYEE 권한 필요
                        .anyRequest().authenticated() // 나머지 경로는 인증 필요
                );

        //필터 추가 LoginFilter()는 인자를 받음 (AuthenticationManager() 메소드에 authenticationConfiguration 객체를 넣어야 함) 따라서 등록 필요
        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration)), UsernamePasswordAuthenticationFilter.class);

        http        //세션 설정
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
