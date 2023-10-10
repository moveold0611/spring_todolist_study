package com.todostudy.todolist.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors(); // WebMVCConfig에서 설정한 cors 정책을 따르겠다.
        http.csrf().disable(); // csrf 토큰 비활성화

        http.authorizeRequests()
                .antMatchers("/auth/**") // 주소값 이하
                .permitAll(); // 요청 허용
//                .anyRequest() // 나머지는??
//                .authenticated() // 인증 받아라
//                .and() // 그리고
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//                .exceptionHandling();
//                .authenticationEntryPoint(authenticateExceptionEntryPoint);

    }
}

