package com.todostudy.todolist.controller;

import com.todostudy.todolist.dto.GetTodoListRespDto;
import com.todostudy.todolist.dto.TodoReqDto;
import com.todostudy.todolist.repository.UserMapper;
import com.todostudy.todolist.security.JwtTokenProvider;
import com.todostudy.todolist.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/todo")
    public ResponseEntity<?> saveTodo(@RequestBody TodoReqDto todoReqDto) {
        System.out.println(todoReqDto);

        todoService.saveTodo(todoReqDto);

        System.out.println("todo 저장 성공");
        return ResponseEntity.ok().body(200);
    }

    @GetMapping("/todos")
    public ResponseEntity<?> getTodoList() {
        return ResponseEntity.ok().body(todoService.getTodoList());
    }

    @PutMapping("/updatetodo")
    public ResponseEntity<?> updateTodo(@RequestBody GetTodoListRespDto getTodoListRespDto) {
        System.out.println("updateController 호출");
        todoService.updateTodo(getTodoListRespDto);
        return ResponseEntity.ok().body(200);
    }

    @DeleteMapping("/deletetodo/{todoId}")
    public ResponseEntity<?> deleteTodo(@PathVariable Integer todoId) {
        System.out.println("deleteController 호출");
        todoService.deleteTodo(todoId);
        return ResponseEntity.ok().body(200);
    }


}
