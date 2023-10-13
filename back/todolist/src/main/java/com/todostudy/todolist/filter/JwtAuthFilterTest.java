package com.todostudy.todolist.filter;

import com.todostudy.todolist.entity.Authority;
import com.todostudy.todolist.entity.Role;
import com.todostudy.todolist.entity.User;
import com.todostudy.todolist.repository.UserMapper;
import com.todostudy.todolist.security.PrincipalUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.security.sasl.AuthenticationException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtAuthFilterTest extends GenericFilter {


    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        String authorization = httpServletRequest.getHeader("Authorization");
        String uri = httpServletRequest.getRequestURI();
        // Bearer 토큰
        System.out.println(authorization);
        System.out.println("주소 에러 테스트 진입");

        if(uri.startsWith("/auth") && !uri.startsWith("/authenticated")) {
            System.out.println("주소 에러 테스트");
            chain.doFilter(req, resp);
            return;
        }
        if(!StringUtils.hasText(authorization)){
            throw new AuthenticationException();
        }
        String accessToken = authorization.substring("Bearer ".length());
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode("H1uc63bskQ1FJ8hgpekYH+L1XUMOl9HAe/CzgqM4q8ClN5xM+MjAFvA45F0qTf5q3gxBG5K21pBLZBvNrHiAEA=="));

        // 빈 토큰일 시 예외 처리
        if(!StringUtils.hasText(accessToken)) {
            throw new AuthenticationException();
        }

        System.out.println(accessToken);
        System.out.println("주소 에러 테스트 이탈 후");

        Claims claims = null;

        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
            System.out.println("try 후");

        }catch(Exception e) {
            chain.doFilter(req, resp);
            System.out.println(e.getStackTrace());
            return;
        }

        System.out.println("claims 전");
        String username = claims.get("username", String.class);
        List<Object> authList = claims.get("auth", List.class);
        List<Authority> authorities = new ArrayList<>();
        System.out.println("claims 후");

        authList.forEach(auth -> {
            Role role = new Role();
            role.setRoleName(((Map<String, String>) auth).get("authority"));
            Authority authority = new Authority();
            authority.setRole(role);
            authorities.add(authority);
        });
        System.out.println("authorities 제작 후");

        User user = User.builder()
                .email(username)
                .authorities(authorities)
                .build();
        System.out.println("user 제작 후");

        PrincipalUser principalUser = new PrincipalUser(user);
        Authentication authentication =
                new UsernamePasswordAuthenticationToken
                        (principalUser, null, principalUser.getAuthorities());
        System.out.println("authentication 제작 후");

        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("SecurityContextHolder 삽입 후");

        chain.doFilter(req, resp);
        System.out.println("최종 chain 후");
        return;
    }
}
