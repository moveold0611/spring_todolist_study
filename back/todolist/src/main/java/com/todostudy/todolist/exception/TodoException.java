package com.todostudy.todolist.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class TodoException extends RuntimeException{
    
    private Map<String, String> errorMap = new HashMap<>();
    
    public TodoException(Map<String, String> errorMap) {
        super("todo 오류");
        System.out.println("todo Excption 클래스 오류 발생");
        this.errorMap = errorMap;
        errorMap.forEach((k, v) -> {
            System.out.println(k + ": " + v);
        });
    }
}
