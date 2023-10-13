package com.todostudy.todolist.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticatedController {

    @GetMapping("/authenticated")
    public ResponseEntity<?> authenticated() {
        System.out.println("Authenicated Get 클래스 테스트");
        return ResponseEntity.ok().body("ㅁㄴㅇㄻㄴㅇㄻㄴㅇㄹ");
    }
}
