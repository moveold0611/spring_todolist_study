package com.todostudy.todolist.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 요청 엔드포인트
                .allowedMethods("*") // 모든 요청 메서드
                .allowedOrigins("*"); // 모든 요청 주소 허용 ex) http://naver.com, localhost:3000
    }
}
