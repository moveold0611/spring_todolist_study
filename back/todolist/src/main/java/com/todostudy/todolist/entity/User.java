package com.todostudy.todolist.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Integer userId;
    private String email;
    private String password;
    private String name;
    private List<Authority> authorities;

    public List<SimpleGrantedAuthority> toGrantedAuthorityList() {
        List<SimpleGrantedAuthority> simpleGrantedAuthorities =
            new ArrayList<>();
        authorities.forEach(auth -> {
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(auth.getRole().getRoleName()));
        });
        return simpleGrantedAuthorities;
    }

}
