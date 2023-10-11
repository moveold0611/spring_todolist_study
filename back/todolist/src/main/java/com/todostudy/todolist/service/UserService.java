package com.todostudy.todolist.service;

import com.todostudy.todolist.dto.SignupReqDto;
import com.todostudy.todolist.entity.User;
import com.todostudy.todolist.exception.SignupException;
import com.todostudy.todolist.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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


}
