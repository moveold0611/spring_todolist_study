package com.todostudy.todolist.dto;

import com.todostudy.todolist.entity.Todo;
import lombok.Data;

@Data
public class TodoReqDto {
    private String email;
    private String todo;

    public Todo toTodoEntity(Integer userId, String todo) {
        Todo todoEntity = Todo.builder()
                .userId(userId)
                .todo(todo)
                .build();
        return todoEntity;
    }
}
