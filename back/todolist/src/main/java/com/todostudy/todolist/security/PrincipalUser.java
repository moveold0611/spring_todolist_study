package com.todostudy.todolist.security;



import com.todostudy.todolist.entity.User;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PrincipalUser implements UserDetails {

    private String email;
    private String password;


    public PrincipalUser(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    // 로그인 권한 3가지
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
//        authorities.forEach(authority ->
//                authorityList.add
//                        (new SimpleGrantedAuthority(authority.getRole().getRoleName())));
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }


    // 계정 만료 -> 사용 기간이 정해진 계정
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠금 -> 비밀번호 x회 이상 틀릴 시, 비정상적인 접근 시
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 계정 자격 증명 만료 -> 공공인증서 만료, 휴대폰 인증 만료
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 활성화 -> 회원가입 후 본인인증 미진행
    @Override
    public boolean isEnabled() {
        return true;
    }
}
