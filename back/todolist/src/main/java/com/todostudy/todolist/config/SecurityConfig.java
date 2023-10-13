package com.todostudy.todolist.config;

import com.todostudy.todolist.exception.AuthenticateExceptionEntryPoint;
import com.todostudy.todolist.filter.JwtAuthFilterTest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticateExceptionEntryPoint authenticateExceptionEntryPoint;
    private final JwtAuthFilterTest jwtAuthFilterTest;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors(); // WebMVCConfig에서 설정한 cors 정책을 따르겠다.
        http.csrf().disable(); // csrf 토큰 비활성화
        System.out.println("시큐리티");
        http.authorizeRequests()
                .antMatchers("/auth/**") // 주소값 이하
                .permitAll() // 요청 허용
                .antMatchers("/admin/**")
                .hasRole("admin")
                .antMatchers("/manager/**")
                .hasRole("manager")
                .anyRequest() // 나머지는??
                .authenticated() // 인증 받아라
                .and() // 그리고
                .addFilterBefore(jwtAuthFilterTest, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(authenticateExceptionEntryPoint);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

