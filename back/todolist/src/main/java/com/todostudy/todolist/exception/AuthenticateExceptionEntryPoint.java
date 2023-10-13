package com.todostudy.todolist.exception;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthenticateExceptionEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse resp, AuthenticationException auth) throws IOException, ServletException {
        resp.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", getErrorMessage(auth));
        JsonMapper jsonMapper = new JsonMapper();
        String errorJsonMessage = jsonMapper.writeValueAsString(errorMap);

        resp.getWriter().println(errorJsonMessage);
    }

    private String getErrorMessage(AuthenticationException authException) {
        if(authException.getClass() == InternalAuthenticationServiceException.class) {
            System.out.println("잘못된 사용자 정보입니다. 다시 확인하세요.");
            return "잘못된 사용자 정보입니다. 다시 확인하세요.";
        }else if(authException.getClass() == BadCredentialsException.class) {
            System.out.println("잘못된 사용자 정보입니다. 다시 확인하세요.");
            return "잘못된 사용자 정보입니다. 다시 확인하세요.";
        }else if(authException.getClass() == AccountExpiredException.class) {
            System.out.println("만료된 사용자 정보입니다. 다시 확인하세요.");
            return "만료된 사용자 정보입니다. 다시 확인하세요.";
        }else if(authException.getClass() == CredentialsExpiredException.class) {
            System.out.println("인증서가 만료되었습니다. 관리자에게 문의하세요.");
            return "인증서가 만료되었습니다. 관리자에게 문의하세요.";
        }else if(authException.getClass() == DisabledException.class) {
            System.out.println("비활성화된 사용자 정보입니다. 관리자에게 문의하세요.");
            return "비활성화된 사용자 정보입니다. 관리자에게 문의하세요.";
        }else if(authException.getClass() == LockedException.class) {
            System.out.println("암호 오류 5회 이상. 관리자에게 문의하세요.");
            return "암호 오류 5회 이상. 관리자에게 문의하세요.";
        }else {
            System.out.println("사용자 정보 오류.");
            return "사용자 정보 오류.";
        }
    }

}
