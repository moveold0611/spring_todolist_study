package com.todostudy.todolist.service;

import com.todostudy.todolist.dto.SigninReqDto;
import com.todostudy.todolist.dto.SignupReqDto;
import com.todostudy.todolist.entity.User;
import com.todostudy.todolist.exception.SigninException;
import com.todostudy.todolist.exception.SignupException;
import com.todostudy.todolist.repository.UserMapper;
import com.todostudy.todolist.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional(rollbackFor = SignupException.class)
    public void signupUser(SignupReqDto signupReqDto) {
        User user = signupReqDto.toUserEntity(passwordEncoder);
        boolean succesAccount = userMapper.saveUser(user) > 0;

        if(!succesAccount) {
            Map<String, String> errorMap = new HashMap<>();
            throw new SignupException(errorMap);
        }
    }

    public boolean checkDuplicated(String email) {
        boolean flag = false;
        flag = userMapper.findUser("email", email) != null;
        return flag;
    }

    public String signinUser(SigninReqDto signinReqDto) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken
                (signinReqDto.getEmail(), signinReqDto.getPassword());

        Authentication authentication
                = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        String accessToken = jwtTokenProvider.generateAccessToken(authentication);

        if(accessToken == null) {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("message", "로그인에 실패하였습니다.");
            throw new SigninException(errorMap);
        }

        return accessToken;
    }


}
