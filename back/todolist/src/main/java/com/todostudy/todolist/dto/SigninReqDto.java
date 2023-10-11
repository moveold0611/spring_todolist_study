package com.todostudy.todolist.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Data
public class SigninReqDto {
    private String email;
    private String password;
}
