package com.todostudy.todolist.dto;

import com.todostudy.todolist.entity.User;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Data
public class SignupReqDto {

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{4,25}$", message = "비밀번호 형식이 올바르지 않습니다.")
    private String password;

    @Pattern(regexp = "^[가-힣]{2,6}$", message = "이름 형식을 다시 확인해주세요.")
    private String name;

    public User toUserEntity(BCryptPasswordEncoder passwordEncoder) {
        return User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .build();
    }
}
