//package com.todostudy.todolist.filter;
//
//
//import com.todostudy.todolist.security.JwtTokenProvider;
//import io.jsonwebtoken.Claims;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//import javax.security.sasl.AuthenticationException;
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//
//@RequiredArgsConstructor
//@Component
//public class JwtAuthFilter extends GenericFilter {
//
//    private final JwtTokenProvider jwtTokenProvider;
//
//    @Override
//    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
//        Claims claims = null;
//        HttpServletRequest httpServletRequest = (HttpServletRequest) req;
//        String uri = httpServletRequest.getRequestURI();
//
//        String token = httpServletRequest.getHeader("Authorization");
//
//        String jwtToken = jwtTokenProvider.convertToken(token);
//        // bearer가 제거된 토큰 => jwtToken
//        System.out.println("필터의 토큰 " + jwtToken);
//
////        // 빈 토큰일 시 예외 처리
////        if(!StringUtils.hasText(jwtToken)) {
////            throw new AuthenticationException();
////        }
//
//        if(!uri.startsWith("/auth") && jwtTokenProvider.validateToken(jwtToken) && uri.startsWith("/authenticated")) {
//            System.out.println("authFilter /authenticated get요청 조건문 테스트");
//            Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//
//        if(uri.startsWith("/auth") & !uri.startsWith("/authenticated")) {
//            chain.doFilter(req, resp);
//            return ;
//        }
//
//    }
//}
