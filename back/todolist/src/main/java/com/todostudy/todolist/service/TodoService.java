package com.todostudy.todolist.service;

import com.todostudy.todolist.dto.GetTodoListRespDto;
import com.todostudy.todolist.dto.TodoReqDto;
import com.todostudy.todolist.entity.Todo;
import com.todostudy.todolist.entity.User;
import com.todostudy.todolist.exception.TodoException;
import com.todostudy.todolist.repository.TodoMapper;
import com.todostudy.todolist.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoMapper todoMapper;
    private final UserMapper userMapper;

    @Transactional(rollbackFor = TodoException.class)
    public void saveTodo(TodoReqDto todoReqDto) {
        System.out.println("todo서비스");

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(email);

        User user = userMapper.findUser("email" , email);
        System.out.println(user);

        Todo todo = Todo.builder()
                .todo(todoReqDto.getContent())
                .userId(user.getUserId())
                .build();
        System.out.println(todo);

        boolean success = todoMapper.postTodo(todo.getUserId(), todo.getTodo()) > 0;

        if(!success) {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("message", "todo 추가 오류");
            throw new TodoException(errorMap);
        }
    }


    public List<GetTodoListRespDto> getTodoList() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<GetTodoListRespDto> getTodoListRespDto = new ArrayList<>();

        todoMapper.getTodoListByUserId(userMapper.findUser("email" , email).getUserId())
                .forEach(todo -> {
                    getTodoListRespDto.add(todo.toTodoListRespDto());
                });
        return getTodoListRespDto;
    }

    @Transactional(rollbackFor = TodoException.class)
    public void updateTodo(GetTodoListRespDto getTodoListRespDto) {

        boolean success = todoMapper.updateTodo(getTodoListRespDto) > 0;
        if(!success) {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("message", "todo 수정 오류");
            throw new TodoException(errorMap);
        }
    }

    @Transactional(rollbackFor = TodoException.class)
    public void deleteTodo(Integer todoId) {

        boolean success = todoMapper.deleteTodo(todoId) > 0;
        if(!success) {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("message", "todo 삭제 오류");
            throw new TodoException(errorMap);
        }
    }


}
