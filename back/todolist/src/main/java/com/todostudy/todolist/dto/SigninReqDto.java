package com.todostudy.todolist.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Data
public class SigninReqDto {
    @Email
    private String email;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{4,25}$", message = "비밀번호 형식이 올바르지 않습니다.")
    private String password;
}
