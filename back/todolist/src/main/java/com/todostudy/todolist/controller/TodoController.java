package com.todostudy.todolist.controller;

import com.todostudy.todolist.dto.TodoReqDto;
import com.todostudy.todolist.repository.UserMapper;
import com.todostudy.todolist.security.JwtTokenProvider;
import com.todostudy.todolist.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/index")
@RequiredArgsConstructor
public class TodoController {

    private final UserMapper userMapper;
    private final TodoService todoService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/posttodo")
    public ResponseEntity<?> saveTodo(@RequestBody TodoReqDto todoReqDto) {
        System.out.println(todoReqDto);

        Integer userId = userMapper.findUser("email" ,todoReqDto.getEmail()).getUserId();

        System.out.println(userId);
        String todo = todoReqDto.getTodo();
        System.out.println(todo);

        todoService.saveTodo(userId, todo);

        System.out.println("todo 저장 성공");
        return ResponseEntity.ok().body(200);
    }


}
