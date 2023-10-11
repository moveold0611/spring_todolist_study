package com.todostudy.todolist.service;

import com.todostudy.todolist.entity.User;
import com.todostudy.todolist.repository.UserMapper;
import com.todostudy.todolist.security.PrincipalUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userMapper.findUser("email", email);
        if(user != null) {
            return new PrincipalUser(user.getEmail(), user.getPassword());
        }
        return null;
    }



}
