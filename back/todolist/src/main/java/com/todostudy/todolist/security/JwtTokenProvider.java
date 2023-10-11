package com.todostudy.todolist.security;

import com.todostudy.todolist.entity.User;
import com.todostudy.todolist.repository.UserMapper;
import com.todostudy.todolist.service.PrincipalDetailService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;

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
            return false;
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
        String username = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody()
                .get("username")
                .toString();
        PrincipalUser principalUser = (PrincipalUser) principalDetailService.loadUserByUsername(username);

        authentication = new UsernamePasswordAuthenticationToken(principalUser, null, principalUser.getAuthorities());
        return authentication;
    }

}