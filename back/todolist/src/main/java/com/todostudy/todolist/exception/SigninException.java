package com.todostudy.todolist.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class SigninException extends RuntimeException{

    private Map<String, String> errorMap;

    public SigninException(Map<String, String> errorMap) {
        super("로그인 오류");
        this.errorMap = errorMap;
        errorMap.forEach((k, v) -> {
            System.out.println(k+ ": " + v);
        });
    }
}
