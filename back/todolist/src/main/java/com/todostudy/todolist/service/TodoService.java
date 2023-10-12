package com.todostudy.todolist.service;

import com.todostudy.todolist.dto.TodoReqDto;
import com.todostudy.todolist.entity.Todo;
import com.todostudy.todolist.exception.TodoException;
import com.todostudy.todolist.repository.TodoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoMapper todoMapper;

    @Transactional(rollbackFor = TodoException.class)
    public void saveTodo(Integer userId, String todo) {
        System.out.println("todo서비스");
        System.out.println(userId);
        System.out.println(todo);
        boolean success = todoMapper.postTodo(userId, todo) > 0;

        if(!success) {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("message", "todo 추가 오류");
            throw new TodoException(errorMap);
        }

    }

}
