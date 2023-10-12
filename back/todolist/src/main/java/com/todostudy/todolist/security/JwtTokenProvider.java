package com.todostudy.todolist.security;

import com.todostudy.todolist.entity.User;
import com.todostudy.todolist.exception.SigninException;
import com.todostudy.todolist.repository.UserMapper;
import com.todostudy.todolist.service.PrincipalDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    private final Key key;
    private final PrincipalDetailService principalDetailService;
    private final UserMapper userMapper;

    public JwtTokenProvider(@Value("${jwt.secret}") String secret,
                            @Autowired PrincipalDetailService principalDetailService,
                            @Autowired UserMapper userMapper) {
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));

        this.principalDetailService = principalDetailService;
        this.userMapper = userMapper;
    }

    // 토큰 생성
    public String generateAccessToken(Authentication authentication) {
        String accessToken = null;
        PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();
        Date tokenExpiresDate = new Date(new Date().getTime() + (1000 * 60 * 60 * 24));

        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject("AccessToken")
                .claim("username", principalUser.getUsername())
                .claim("auth", principalUser.getAuthorities())
                .setExpiration(tokenExpiresDate)
                .signWith(key, SignatureAlgorithm.HS256);

        User user = userMapper.findUser("email" ,principalUser.getUsername());
        if(user != null) {
            return jwtBuilder.claim("username", user.getEmail()).compact();
        }
        return accessToken;
    }

    // 토큰 유효성 검사 (위조 방지, 기간 만료 검사 등)
    public Boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
        }catch (Exception e) {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("message", "만료된 토큰입니다");
            throw new SigninException(errorMap);
        }
        return true;
    }

    public String convertToken(String bearerToken) {
        String type = "Bearer ";
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(type)) {
            // hasText => null체크, 공백확인 동시에 해준다
            return bearerToken.substring(type.length());
        }
        return null;
    }

    public Authentication getAuthentication(String accessToken) {
        Authentication authentication = null;
        Claims claims = null;
        try {
            String username = Jwts
                    .parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody()
                    .get("email")
                    .toString();
        }catch (Exception e) {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("message", "토큰 에러");
            throw new SigninException(errorMap);
        }
        String username = claims.get("username", String.class);
        PrincipalUser principalUser = (PrincipalUser) principalDetailService.loadUserByUsername(username);

        authentication = new UsernamePasswordAuthenticationToken(principalUser, null, principalUser.getAuthorities());
        return authentication;
    }

}
